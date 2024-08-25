package com.example.arten.utils

import com.example.arten.data.ResponseData
import com.example.arten.data.auth.LoginRequest
import com.example.arten.data.auth.RegisterRequest
import com.example.arten.data.otp.OTPActivateRequest
import com.example.arten.data.otp.OTPRequest
import com.example.arten.data.transcribe.TranscribeResponse
import com.example.arten.data.translate.TranslateRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

const val API_KEY = "00jhJ_YAsU90J3hpG-vNI1I9QBm_Voefj8NMcR-OEY8"

interface Api {
    
    @POST("users/login")
    fun userLogin(
        @Body request: LoginRequest
    ):Call<ResponseData>
    
    @POST("users/register")
    fun userRegister(
        @Body request: RegisterRequest
    ):Call<ResponseData>
    
    @POST("users/otp")
    fun sendOtp(
        @Body request: OTPRequest
    ):Call<ResponseData>
    
    @POST("users/activate")
    fun activateOtp(
        @Body request: OTPActivateRequest
    ):Call<ResponseData>
    
    @POST("translations")
    fun translateText(
        @Body request: TranslateRequest
    ):Call<ResponseData>
    
    @GET("translations")
    fun history(
        @Header("Authorization") token: String
    ):Call<ResponseData>
    
    @Headers("x-api-key: $API_KEY")
    @Multipart
    @POST("id/transcribe")
    fun transcribeVoiceID(
        @Part file: MultipartBody.Part,
    ):Call<TranscribeResponse>
    
    @Headers("x-api-key: $API_KEY")
    @Multipart
    @POST("eng/transcribe")
    fun transcribeVoiceENG(
        @Part file: MultipartBody.Part,
    ):Call<TranscribeResponse>
}