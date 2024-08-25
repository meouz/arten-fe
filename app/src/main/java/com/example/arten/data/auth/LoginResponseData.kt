package com.example.arten.data.auth

import com.google.gson.annotations.SerializedName

data class LoginResponseData(
    val username: String,
    @SerializedName("jwtToken") val token: String,
)