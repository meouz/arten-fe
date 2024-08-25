package com.example.arten.data.auth

data class RegisterResponseData(
    val id: String,
    val name: String,
    val username: String,
    val password: String,
    val email: String,
    val isVerified: Boolean,
    val createdAt: String,
)