package com.example.arten.model.model.auth

data class ResponseLogin(
    val status: String,
    val error: String,
    val message: String,
    val token: String,
    val username: String,
    val email: String,
)
