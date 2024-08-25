package com.example.arten.data.auth

data class RegisterRequest(
    val name: String,
    val username: String,
    val password: String,
    val email: String,
)