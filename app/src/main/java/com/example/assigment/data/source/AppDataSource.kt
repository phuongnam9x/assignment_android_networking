package com.example.assigment.data.source

import android.net.Uri
import com.example.assigment.data.model.Users
import com.example.assigment.data.source.remote.body.ProductBody
import com.example.assigment.data.source.remote.response.BaseResponse
import com.example.assigment.data.source.remote.response.LoginResponse
import com.example.assigment.data.source.remote.response.ProductsResponse
import com.example.assigment.data.source.remote.response.RegisterResponse
import retrofit2.http.Query

interface AppDataSource {

    interface Remote {
        suspend fun login(userName: String, password: String): BaseResponse<LoginResponse>
        suspend fun register(user: Users): BaseResponse<RegisterResponse>
        suspend fun getProducts(): BaseResponse<ProductsResponse>

        // Product
        suspend fun saveProduct(  title: String?,
                                  deception: String?,
                                  price: Double?,
                                  imageUri: Uri?
        ): BaseResponse<Any>

        suspend fun editProduct(
            id: String,
            title: String?,
            deception: String?,
            price: Double?,
            imageUri: Uri?
        ): BaseResponse<Any>

        suspend fun deleteProduct(id: String): BaseResponse<Any>
        suspend fun getProductByName( title: String?): BaseResponse<ProductsResponse>
    }
}