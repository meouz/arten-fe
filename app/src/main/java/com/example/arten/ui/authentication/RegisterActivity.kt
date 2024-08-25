package com.example.arten.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.databinding.ActivityRegisterBinding
import com.example.arten.data.ResponseData
import com.example.arten.data.auth.RegisterRequest
import com.example.arten.data.auth.RegisterResponseData
import com.example.arten.utils.PrefManager
import com.example.arten.utils.RClient
import com.example.arten.ui.otp.OtpSendActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var prefManager: PrefManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefManager = PrefManager(this)
        
        binding.btnRegister.setOnClickListener {
            register()
        }
        
        binding.tvLogin.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    
    private fun register() {
        
        val name = binding.etName.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        if (name.isEmpty()) {
            binding.etName.error = "Name is required"
            binding.etName.requestFocus()
            return
        }
        
        if (username.isEmpty()) {
            binding.etUsername.error = "Username is required"
            binding.etUsername.requestFocus()
            return
        }
        
        if (isEmailValid(email)) {
            binding.etEmail.error = "Email is required"
            binding.etEmail.requestFocus()
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
        
        RClient.instance.userRegister(RegisterRequest(name, username, password, email))
            .enqueue(object : Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>,
                ) {
                    Log.d("RegisterActivity", "onResponse: ${call.request()}")
                    Log.d("RegisterActivity", "onResponse: ${response.body()}")
                    if (response.isSuccessful) {
                        val body = response.body()
                        
                        body?.let {
                            if (it.data is Map<*, *>) {
                                val gson = Gson()
                                val jsonData = gson.toJsonTree(it.data).asJsonObject
                                val responseData =
                                    gson.fromJson(jsonData, RegisterResponseData::class.java)
                                
                                prefManager.setUsername(responseData.username)
                                prefManager.setEmail(responseData.email)
                                
                                finish()
                                startActivity(
                                    Intent(
                                        this@RegisterActivity, OtpSendActivity::class.java
                                    )
                                )
                            }
                        }
                    } else {
                        Toast.makeText(this@RegisterActivity, "Register failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Register failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
    
    private fun isEmailValid(email: String): Boolean {
        return email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}