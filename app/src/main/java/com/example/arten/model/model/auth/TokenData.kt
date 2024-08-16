package com.example.arten.model.model.auth

import com.google.gson.annotations.SerializedName

data class AuthRData(
    @SerializedName("jwtToken") val token: String
)