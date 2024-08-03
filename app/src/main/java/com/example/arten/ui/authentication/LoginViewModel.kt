package com.example.arten.ui.authentication

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    
    fun login(email: String, password: String) = when {
        email.isEmpty() || password.isEmpty() -> {
            // Display error message
        }
        
        email.split("@").equals("@gmail.com") -> {
            // Display error message
        }
        
        password.length < 8 -> {
            // Display error message
        }
        
        else -> {
            // Authenticate user
        }
    }
    
    fun register(username: String, email: String, password: String, confirmPassword: String) = when {
        username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
            // Display error message
        }
        
        email.split("@").equals("@gmail.com") && email.length < 9 -> {
            // Display error message
        }
        
        password.length < 8 -> {
            // Display error message
        }
        
        password != confirmPassword -> {
            // Display error message
        }
        
        else -> {
            // Register user
        }
    }
}