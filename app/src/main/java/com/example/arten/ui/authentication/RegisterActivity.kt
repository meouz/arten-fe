package com.example.arten.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.databinding.ActivityRegisterBinding
import com.example.arten.model.model.auth.RClient
import com.example.arten.model.model.auth.data.RegisterRequest
import com.example.arten.model.model.auth.data.RegisterResponse
import com.example.arten.model.model.auth.data.RegisterResponseData
import com.example.arten.model.model.pref.PrefManager
import com.example.arten.ui.otp.OtpSendActivity
import com.google.gson.Gson
import org.json.JSONObject
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
        
        binding.btnRegister.setOnClickListener {
            register()
        }
        
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
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
        
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            binding.etEmail.requestFocus()
            return
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            binding.etPassword.requestFocus()
            return
        }
        
        RClient.instance.userRegister(RegisterRequest(name, username, password, email))
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        
                        body?.let {
                            if (it.data is Map<*, *>) {
                                val gson = Gson()
                                val jsonData = gson.toJsonTree(it.data).asJsonObject
                                val responseData =
                                    gson.fromJson(jsonData, RegisterResponseData::class.java)
                                
                                prefManager.setUsername(responseData.username)
                                
                                startActivity(
                                    Intent(
                                        this@RegisterActivity,
                                        OtpSendActivity::class.java
                                    )
                                )
                                finish()
                            }
                        }
                    } else {
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        val messageError = JSONObject(jsonObj.getString("message"))
                        
                        if (messageError.getString("message") == "Email already used") {
                            binding.etEmail.error = messageError.getString("error")
                            binding.etPassword.text?.clear()
                            binding.etEmail.requestFocus()
                        }
                    }
                }
                
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Register failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}