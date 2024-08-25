package com.example.arten.domain.repository

import android.util.Log
import com.example.arten.data.ResponseData
import com.example.arten.data.transcribe.TranscribeResponse
import com.example.arten.data.translate.TranslateRequest
import com.example.arten.utils.RClient
import com.example.arten.utils.RClientVoice
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TranslateRepository {
    
    fun translateText(originLanguage: String, languageResult: String, text: String): String {
        var result = ""
        RClient.instance.translateText(TranslateRequest(originLanguage, languageResult, text))
            .enqueue(object :
                Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>,
                ) {
                    Log.d("Translate", "onRequest: ${call.request()}")
                    Log.d("Translate", "onResponse: ${response.body()}")
                    if (response.isSuccessful) {
                        // Record sent successfully
                        result = response.body()?.data.toString()
                        Log.d("Translate", "onResponse: ${response.body()}")
                    } else {
                        // Record sending failed
                        Log.d("Translate", "onResponse: ${response.message()}")
                    }
                }
                
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.d("Translate", "onFailure: ${t.message}")
                }
            })
        
        return result
    }
    
    fun transcribeVoice(file: MultipartBody.Part, originLanguage: String): String {
        var result = ""
        when (originLanguage) {
            "Indonesia" -> {
                RClientVoice.instance.transcribeVoiceID(file).enqueue(object :
                    Callback<TranscribeResponse> {
                    override fun onResponse(
                        call: Call<TranscribeResponse>,
                        response: Response<TranscribeResponse>,
                    ) {
                        Log.d("Transcribe", "onRequest: ${call.request()}")
                        Log.d("Transcribe", "onResponse: ${response.body()}")
                        if (response.isSuccessful) {
                            // Record sent successfully
                            result = response.body()?.transcription.toString()
                            Log.d("Transcribe", "onResponse: ${response.body()}")
                        } else {
                            // Record sending failed
                            Log.d("Transcribe", "onResponse: ${response.body()}")
                        }
                    }
                    
                    override fun onFailure(call: Call<TranscribeResponse>, t: Throwable) {
                        Log.d("Transcribe", "onFailure: ${t.message}")
                    }
                })
            }
            
            "English" -> {
                RClientVoice.instance.transcribeVoiceENG(file).enqueue(object :
                    Callback<TranscribeResponse> {
                    override fun onResponse(
                        call: Call<TranscribeResponse>,
                        response: Response<TranscribeResponse>,
                    ) {
                        Log.d("Transcribe", "onRequest: ${call.request()}")
                        Log.d("Transcribe", "onResponse: ${response.body()}")
                        if (response.isSuccessful) {
                            // Record sent successfully
                            result = response.body()?.transcription.toString()
                            Log.d("Transcribe", "onResponse: ${response.body()}")
                        } else {
                            // Record sending failed
                            Log.d("Transcribe", "onResponse: ${response.message()}")
                        }
                    }
                    
                    override fun onFailure(call: Call<TranscribeResponse>, t: Throwable) {
                        Log.d("Transcribe", "onFailure: ${t.message}")
                    }
                })
            }
        }
        
        return result
    }
    
    fun history(token: String) {
        RClient.instance.history("Bearer $token").enqueue(object :
            Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>,
            ) {
                Log.d("History", "onRequest: ${call.request()}")
                Log.d("History", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    // Record sent successfully
                    Log.d("History", "onResponse: ${response.body()}")
                } else {
                    // Record sending failed
                    Log.d("History", "onResponse: ${response.message()}")
                }
            }
            
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.d("History", "onFailure: ${t.message}")
            }
        })
    }
}