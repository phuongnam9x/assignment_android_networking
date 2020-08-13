package com.example.assigment.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("image_path")
    val imagePath: String
)