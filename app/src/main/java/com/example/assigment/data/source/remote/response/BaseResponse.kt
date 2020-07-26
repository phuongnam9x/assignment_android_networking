package com.example.assigment.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.sql.ClientInfoStatus
import java.util.*

data class BaseResponse<Data  : Any>(
    @SerializedName("data") private val data: Data,
    @SerializedName("messages") val message: String,
    @SerializedName("status_code") val statusCode: Int
){
    fun unwrap(): Data{
        return if (statusCode == 200) data
        else throw Throwable(message)
    }
}