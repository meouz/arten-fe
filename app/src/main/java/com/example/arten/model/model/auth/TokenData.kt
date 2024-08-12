package com.example.arten.model.model.auth

import com.google.gson.annotations.SerializedName

data class AuthRData(
    @SerializedName("token") val token: String
)