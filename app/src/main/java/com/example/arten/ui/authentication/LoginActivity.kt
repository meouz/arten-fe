package com.example.arten.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.databinding.ActivityLoginBinding
import com.example.arten.utils.PrefManager
import com.example.arten.ui.otp.OtpSendActivity
import com.example.arten.ui.translate.TranslateActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefManager = PrefManager(this)
        
        Log.d("TranslateActivity", "Email: ${prefManager.getEmail()}")
        Log.d("TranslateActivity", "Username: ${prefManager.getUsername()}")
        Log.d("TranslateActivity", "Token: ${prefManager.getToken()}")
        Log.d("TranslateActivity", "LoggedIn: ${prefManager.isLogin()}")
        Log.d("TranslateActivity", "Verified: ${prefManager.isVerified()}")
        
        if (prefManager.isLogin()) {
            finish()
            startActivity(Intent(this, TranslateActivity::class.java))
        }

        if (!prefManager.isVerified() && prefManager.getUsername() != null) {
            finish()
            startActivity(Intent(this, OtpSendActivity::class.java))
        }
        
        binding.btnLogin.setOnClickListener {
            login()
        }
        
        binding.tvRegister.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    
    private fun login() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        if (username.isEmpty()) {
            binding.etUsername.error = "Username is required"
            binding.etUsername.requestFocus()
            return
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            binding.etPassword.requestFocus()
            return
        }
        
        
    }
}