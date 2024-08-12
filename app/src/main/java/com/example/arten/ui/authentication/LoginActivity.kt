package com.example.arten.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.databinding.ActivityLoginBinding
import com.example.arten.model.model.auth.RClient
import com.example.arten.model.model.auth.ResponseLogin
import com.example.arten.model.model.pref.PrefManager
import com.example.arten.ui.translate.TranslateActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
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
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
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
        
        RClient.instance.checkUserLogin(password, email).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        prefManager.setLogin(true)
                        prefManager.setUsername(body.username)
                        prefManager.setToken(body.token)
                        prefManager.setEmail(body.email)
                        
                        startActivity(Intent(this@LoginActivity, TranslateActivity::class.java))
                        finish()
                    }
                } else {
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    val messageError = JSONObject(jsonObj.getString("message"))
                    
                    if (messageError.getString("message") == "Invalid email") {
                        binding.etEmail.error = messageError.getString("error")
                        binding.etPassword.text?.clear()
                        binding.etEmail.requestFocus()
                    }
                }
            }
            
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}