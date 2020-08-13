package com.example.assigment.data.source.remote.body

import com.google.gson.annotations.SerializedName


data class ProductBody(
    @SerializedName( "title")
    val title: String?,
    @SerializedName( "deception")
    val deception: String?,
    @SerializedName(  "price")
    val price: Double?,
    @SerializedName(  "image_path")
    val imagePath: String?
)