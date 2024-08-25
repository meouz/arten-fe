package com.example.arten.domain.repository

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.arten.data.ResponseData
import com.example.arten.data.auth.LoginRequest
import com.example.arten.data.auth.LoginResponseData
import com.example.arten.utils.PrefManager
import com.example.arten.utils.RClient
import com.example.arten.ui.translate.TranslateActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    
    private val prefManager = PrefManager()
    
    fun login(username: String, password: String) {
        RClient.instance.userLogin(LoginRequest(username, password))
            .enqueue(object : Callback<ResponseData> {
                
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>,
                ) {
                    Log.d("LoginActivity", "onResponse: ${call.request()}")
                    Log.d("LoginActivity", "onResponse: ${response.body()}")
                    if (response.isSuccessful) {
                        val body = response.body()
                        
                        body?.let {
                            if (it.data is Map<*, *>) {
                                val gson = Gson()
                                val jsonData = gson.toJsonTree(it.data).asJsonObject
                                val responseData =
                                    gson.fromJson(jsonData, LoginResponseData::class.java)
                                
                                prefManager.setUsername(responseData.username)
                                prefManager.setToken(responseData.token)
                                prefManager.setVerified(true)
                                prefManager.setLogin(true)
                                
                                finish()
                                startActivity(
                                    Intent(
                                        this@LoginActivity,
                                        TranslateActivity::class.java
                                    )
                                )
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    body.message + " " + body.data,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    Log.d("LoginActivity", "onFailure: ${t.message}")
                }
            })
    }
}