package com.example.assigment.data.source.remote.Api

import com.example.assigment.data.Model.Users
import com.example.assigment.data.source.remote.response.BaseResponse
import com.example.assigment.data.source.remote.response.LoginResponse
import com.example.assigment.data.source.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {
    @POST("")
    suspend fun login(
        @Field("user_name") userName: String,
        @Field("password") password: String
    ):BaseResponse<LoginResponse>


    @POST("")
    suspend fun register(@Body  Users: Users):BaseResponse<RegisterResponse>
}