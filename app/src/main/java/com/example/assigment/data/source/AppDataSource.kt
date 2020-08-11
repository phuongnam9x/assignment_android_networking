package com.example.assigment.data.source

import com.example.assigment.data.model.Users
import com.example.assigment.data.source.remote.response.BaseResponse
import com.example.assigment.data.source.remote.response.LoginResponse
import com.example.assigment.data.source.remote.response.ProductsResponse
import com.example.assigment.data.source.remote.response.RegisterResponse

interface AppDataSource {

    interface Remote {
        suspend fun login(userName: String, password: String): BaseResponse<LoginResponse>
        suspend fun register(user: Users): BaseResponse<RegisterResponse>
        suspend fun getProducts(): BaseResponse<ProductsResponse>
    }
}