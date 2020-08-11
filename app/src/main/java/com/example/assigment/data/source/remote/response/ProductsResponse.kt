package com.example.assigment.data.source.remote.response

import com.example.assigment.data.model.Product
import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("products")
    val products: List<Product> = listOf()
)