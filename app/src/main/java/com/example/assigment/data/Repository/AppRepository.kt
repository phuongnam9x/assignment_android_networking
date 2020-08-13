package com.example.assigment.data.Repository

import android.net.Uri
import com.example.assigment.data.model.Users
import com.example.assigment.data.source.AppDataSource
import com.example.assigment.data.source.remote.response.BaseResponse
import com.example.assigment.data.source.remote.response.LoginResponse
import com.example.assigment.data.source.remote.response.ProductsResponse
import com.example.assigment.data.source.remote.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository private constructor(
    private val remote: AppDataSource.Remote
) {
    suspend fun login(userName: String, password: String): BaseResponse<LoginResponse> {
        return remote.login(userName, password)
    }

    suspend fun register(user: Users): BaseResponse<RegisterResponse> {
        return remote.register(user)
    }

    suspend fun getProducts(): BaseResponse<ProductsResponse> {
        return remote.getProducts()
    }

    // Product
    suspend fun saveProduct(
        title: String?,
        deception: String?,
        price: Double?,
        imageUri: Uri?
    ): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            remote.saveProduct(title, deception, price, imageUri)
        }
    }

    suspend fun editProduct(
        id: String,
        title: String?,
        deception: String?,
        price: Double?,
        imageUri: Uri?
    ): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            remote.editProduct(id, title, deception, price, imageUri)
        }
    }

    suspend fun deleteProduct(id: String): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            remote.deleteProduct(id)
        }
    }

    companion object {
        private var INSTANCE: AppRepository? = null
        fun getInstance(remote: AppDataSource.Remote) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppRepository(remote).also { INSTANCE = it }
            }
    }

}