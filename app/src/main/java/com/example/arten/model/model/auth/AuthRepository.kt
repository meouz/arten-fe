package com.example.arten.model.model.auth

import android.hardware.biometrics.BiometricManager.Strings

interface AuthRepository {
    suspend fun signUp(email: Strings, password: Strings): AuthResult<Unit>
    suspend fun signIn(username: Strings, email: Strings, password: Strings, confirmPassword: Strings): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}