package com.example.assigment.data.source.remote

import com.example.assigment.data.Model.Users
import com.example.assigment.data.source.AppDataSource
import com.example.assigment.data.source.remote.Api.AppService
import com.example.assigment.data.source.remote.response.BaseResponse
import com.example.assigment.data.source.remote.response.LoginResponse
import com.example.assigment.data.source.remote.response.RegisterResponse

class AppRemoteDataSource private constructor(private val appService: AppService):
    AppDataSource.Remote {
    override suspend fun login(userName: String, password: String): BaseResponse<LoginResponse> {
        return appService.login(userName, password)
    }

    override suspend fun register(user: Users): BaseResponse<RegisterResponse> {
        return appService.register(user)
    }
    companion object{
        private var INSTANCE: AppRemoteDataSource?= null
        fun getInstance(appService: AppService): AppRemoteDataSource =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: AppRemoteDataSource(appService).also { INSTANCE = it }
            }

            }
    }


