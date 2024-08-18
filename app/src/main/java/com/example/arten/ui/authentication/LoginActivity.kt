package com.example.arten.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.databinding.ActivityLoginBinding
import com.example.arten.model.network.RClient
import com.example.arten.model.model.auth.LoginRequest
import com.example.arten.model.model.auth.LoginResponse
import com.example.arten.model.model.auth.LoginResponseData
import com.example.arten.model.network.PrefManager
import com.example.arten.ui.translate.TranslateActivity
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefManager = PrefManager(this)
        
        if (prefManager.isLogin()) {
            startActivity(Intent(this, TranslateActivity::class.java))
            finish()
        }
        
        binding.btnLogin.setOnClickListener {
            login()
        }
        
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    
    private fun login() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        if (username.isEmpty()) {
            binding.etUsername.error = "Email is required"
            binding.etUsername.requestFocus()
            return
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            binding.etPassword.requestFocus()
            return
        }
        
        if (password.length < 8) {
            binding.etPassword.error = "Password must be at least 8 characters"
            binding.etPassword.requestFocus()
            return
        }
        
        RClient.instance.userLogin(LoginRequest(username, password))
            .enqueue(object : Callback<LoginResponse> {
                
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        
                        body?.let {
                            if (it.data is Map<*, *>) {
                                val gson = Gson()
                                val jsonData = gson.toJsonTree(it.data).asJsonObject
                                val responseData = gson.fromJson(jsonData, LoginResponseData::class.java)
                                
                                prefManager.setLogin(true)
                                prefManager.setUsername(responseData.username)
                                prefManager.setToken(responseData.token)
                                
                                startActivity(
                                    Intent(
                                        this@LoginActivity,
                                        TranslateActivity::class.java
                                    )
                                )
                                finish()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    body.message + " " + body.data,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        val messageError = JSONObject(jsonObj.getString("message"))
                        
                        if (messageError.getString("message") == "Invalid email") {
                            binding.etUsername.error = messageError.getString("error")
                            binding.etPassword.text?.clear()
                            binding.etUsername.requestFocus()
                        }
                    }
                }
                
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            })
    }
    
    fun Login(view: View) {}
    fun goRegister(view: View) {}
}