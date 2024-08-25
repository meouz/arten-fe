package com.example.arten.data.translate

data class TranslateRequest(
    val originLanguage: String,
    val targetLanguage: String,
    val word: String,
)