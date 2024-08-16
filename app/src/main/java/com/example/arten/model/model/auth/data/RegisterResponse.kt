package com.example.arten.model.model.auth.data

data class RegisterResponse(
    val status: Any,
    val message: String,
    val data: Any,
)

data class RegisterResponseStatus(
    val code: Int,
    val isSuccess: Boolean,
)

data class RegisterResponseData(
    val id: String,
    val name: String,
    val username: String,
    val password: String,
    val email: String,
    val isVerified: Boolean,
    val createdAt: String,
)