package com.example.arten.model.model.auth

import com.example.arten.model.model.auth.data.LoginRequest
import com.example.arten.model.model.auth.data.LoginResponse
import com.example.arten.model.model.auth.data.OTPActivateRequest
import com.example.arten.model.model.auth.data.OTPActivateResponse
import com.example.arten.model.model.auth.data.OTPRequest
import com.example.arten.model.model.auth.data.OTPResponse
import com.example.arten.model.model.auth.data.RegisterRequest
import com.example.arten.model.model.auth.data.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    
    @POST("login")
    fun userLogin(
        @Body request: LoginRequest
    ):Call<LoginResponse>
    
    @POST("register")
    fun userRegister(
        @Body request: RegisterRequest
    ):Call<RegisterResponse>
    
    @POST("verify")
    fun sendOtp(
        @Body request: OTPRequest
    ):Call<OTPResponse>
    
    @POST("activate")
    fun activateOtp(
        @Body request: OTPActivateRequest
    ):Call<OTPActivateResponse>
}