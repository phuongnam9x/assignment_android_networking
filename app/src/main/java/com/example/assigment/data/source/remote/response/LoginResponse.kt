package com.example.assigment.data.source.remote.response

import com.example.assigment.data.Model.Users
import com.google.gson.annotations.SerializedName

data class LoginResponse (@SerializedName("user") val users: Users)