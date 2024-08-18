package com.example.arten.model.network

import com.example.arten.model.model.auth.LoginRequest
import com.example.arten.model.model.auth.LoginResponse
import com.example.arten.model.model.auth.RegisterRequest
import com.example.arten.model.model.auth.RegisterResponse
import com.example.arten.model.model.otp.OTPActivateRequest
import com.example.arten.model.model.otp.OTPActivateResponse
import com.example.arten.model.model.otp.OTPRequest
import com.example.arten.model.model.otp.OTPResponse
import com.example.arten.model.model.translate.TranslateRequest
import com.example.arten.model.model.translate.TranslateResponse
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
    
    @POST("translate")
    fun sendAudio(
        @Body request: TranslateRequest
    ):Call<TranslateResponse>
}