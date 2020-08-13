package com.example.assigment.data.source.remote.Api

import com.example.assigment.data.model.Users
import com.example.assigment.data.source.remote.body.ProductBody
import com.example.assigment.data.source.remote.response.*
import okhttp3.MultipartBody
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
    // Product

    @GET("/api/product/all")
    suspend fun getProducts(): BaseResponse<ProductsResponse>

    @POST("api/product/add")
    suspend fun saveProduct(@Body productBody: ProductBody): BaseResponse<Any>

    @POST("api/product/edit/{id}")
    suspend fun editProduct(
        @Path("id") id: String,
        @Body productBody: ProductBody
    ): BaseResponse<Any>

    @Multipart
    @POST("api/product/upload")
    suspend fun uploadImage(@Part body: MultipartBody.Part): BaseResponse<ImageResponse>

    @POST("api/product/delete/{id}")
    suspend fun deleteProduct(@Path("id") id: String): BaseResponse<Any>
}