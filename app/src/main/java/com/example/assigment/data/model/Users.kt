package com.example.assigment.data.model

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("role_id")
    val roleId: Int

)