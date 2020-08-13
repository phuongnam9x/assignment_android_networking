package com.example.assigment.data.source.remote

import android.app.Application
import android.net.Uri
import android.provider.OpenableColumns
import com.example.assigment.data.model.Users
import com.example.assigment.data.source.AppDataSource
import com.example.assigment.data.source.remote.Api.AppService
import com.example.assigment.data.source.remote.body.ProductBody
import com.example.assigment.data.source.remote.response.BaseResponse
import com.example.assigment.data.source.remote.response.LoginResponse
import com.example.assigment.data.source.remote.response.ProductsResponse
import com.example.assigment.data.source.remote.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class AppRemoteDataSource private constructor(
    private val appService: AppService,
    private val application: Application
) :
    AppDataSource.Remote {
    override suspend fun login(userName: String, password: String): BaseResponse<LoginResponse> {
        return appService.login(userName, password)
    }

    override suspend fun register(user: Users): BaseResponse<RegisterResponse> {
        return appService.register(user)
    }

    override suspend fun getProducts(): BaseResponse<ProductsResponse> {
        return return withContext(Dispatchers.IO) { appService.getProducts() }
    }

    override suspend fun saveProduct(
        title: String?,
        deception: String?,
        price: Double?,
        imageUri: Uri?
    ): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            appService.saveProduct(ProductBody(title, deception, price, uploadAvatar(imageUri)))
        }
    }

    override suspend fun editProduct(
        id: String,
        title: String?,
        deception: String?,
        price: Double?,
        imageUri: Uri?
    ): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            appService.editProduct(id, ProductBody(title, deception, price, uploadAvatar(imageUri)))
        }
    }

    override suspend fun deleteProduct(id: String): BaseResponse<Any> {
        return withContext(Dispatchers.IO) { appService.deleteProduct(id) }
    }

    private suspend fun uploadAvatar(image: Uri?): String? {
        return withContext(Dispatchers.IO) {
            if (image != null) {
                val contentResolver = application.contentResolver
                val type = contentResolver.getType(image)!!
                val inputStream = contentResolver.openInputStream(image)!!
                val fileName = contentResolver.query(image, null, null, null, null)!!.use {
                    it.moveToFirst()
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
                val bytes = ByteArrayOutputStream().use {
                    inputStream.copyTo(it)
                    it.toByteArray()
                }
                val requestFile = bytes.toRequestBody(type.toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", fileName, requestFile)
                appService.uploadImage(body).unwrap().imagePath
            } else {
                null
            }
        }
    }

    companion object {
        private var INSTANCE: AppRemoteDataSource? = null
        fun getInstance(appService: AppService, application: Application): AppRemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppRemoteDataSource(appService, application).also { INSTANCE = it }
            }

    }
}


