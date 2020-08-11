package com.example.assigment.data.source.remote.Api

import com.example.assigment.data.model.Users
import com.example.assigment.data.source.remote.response.BaseResponse
import com.example.assigment.data.source.remote.response.LoginResponse
import com.example.assigment.data.source.remote.response.ProductsResponse
import com.example.assigment.data.source.remote.response.RegisterResponse
import retrofit2.http.*

interface AppService {
    @FormUrlEncoded
    @POST("/api/user/login")
    suspend fun login(
        @Field("user_name") userName: String,
        @Field("password") password: String
    ): BaseResponse<LoginResponse>


    @POST("/api/user/register")
    suspend fun register(@Body Users: Users): BaseResponse<RegisterResponse>

    @GET("/api/product/all")
    suspend fun getProducts(): BaseResponse<ProductsResponse>
}