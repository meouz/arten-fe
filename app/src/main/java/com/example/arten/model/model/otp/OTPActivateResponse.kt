package com.example.arten.model.model.otp

import com.google.gson.annotations.SerializedName

data class OTPActivateResponse(
    val status: Any,
    val message: String,
    val data: Any,
)

data class OTPActivateResponseStatus(
    val code: Int,
    val isSuccess: Boolean,
)

data class OTPActivateResponseData(
    val username: String,
    @SerializedName("jwtToken") val token: String,
)