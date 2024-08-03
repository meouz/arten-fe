package com.example.arten.model.model

data class AuthState(
    val isLoading: Boolean = false,
    val signUpUsername: String = "",
    val signUpPassword: String = "",
    val signInUsername: String = "",
    val signInPassword: String = "",
)