package com.example.arten.model.model.auth

import android.hardware.biometrics.BiometricManager

class AuthRepositoryImpl(
    private val api: AuthApi,
) : AuthRepository {
    
    override suspend fun signUp(
        email: BiometricManager.Strings,
        password: BiometricManager.Strings,
    ): AuthResult<Unit> {
        TODO("Not yet implemented")
    }
    
    override suspend fun signIn(
        username: BiometricManager.Strings,
        email: BiometricManager.Strings,
        password: BiometricManager.Strings,
        confirmPassword: BiometricManager.Strings
    ): AuthResult<Unit> {
        TODO("Not yet implemented")
    }
    
    override suspend fun authenticate(): AuthResult<Unit> {
        TODO("Not yet implemented")
    }
}