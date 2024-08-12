package com.example.arten.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnRegister.setOnClickListener {
            register()
        }
        
        binding.tvLogin.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    
    private fun register() {
    }
}