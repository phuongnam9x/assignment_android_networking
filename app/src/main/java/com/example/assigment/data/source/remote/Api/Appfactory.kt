package com.example.assigment.data.source.remote.Api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.internal.tls.OkHostnameVerifier
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Appfactory {
    companion object{
        val instance: AppService by lazy {
            retrofitBuilder.create(AppService::class.java)
        }
        private const val TIME_OUT = 15000L
        private val client = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        private val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://192.168.1.53:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        }
    }
