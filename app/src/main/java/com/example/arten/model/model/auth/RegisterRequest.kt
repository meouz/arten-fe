package com.example.arten.model.model.auth

data class RegisterRequest(
    val name: String,
    val username: String,
    val password: String,
    val email: String,
)