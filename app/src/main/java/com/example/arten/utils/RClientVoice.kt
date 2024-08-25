package com.example.arten.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RClientVoice {
    private const val BASE_URL = "https://xt1n4hvp-8000.asse.devtunnels.ms/"
    
    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        retrofit.create(Api::class.java)
    }
}