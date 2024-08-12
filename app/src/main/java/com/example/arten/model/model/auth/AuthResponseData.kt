package com.example.arten.model.model.auth

import com.google.gson.annotations.SerializedName

data class AuthResponseData(
    @SerializedName("status") val status: String,
    val data: List<AuthRData>
)
