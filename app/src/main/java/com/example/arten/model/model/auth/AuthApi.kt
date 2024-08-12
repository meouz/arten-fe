package com.example.arten.model.model.auth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {
    
    @GET("login")
    fun checkUserLogin(
        @Query("password") password: String,
        @Query("email") email: String
    ):Call<ResponseLogin>
}