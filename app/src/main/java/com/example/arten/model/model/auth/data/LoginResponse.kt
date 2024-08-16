package com.example.arten.model.model.auth.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val status: Any,
    val message: String,
    val data: Any,
)

data class LoginResponseStatus(
    val code: Int,
    val isSuccess: Boolean,
)

data class LoginResponseData(
    val username: String,
    @SerializedName("jwtToken") val token: String,
)