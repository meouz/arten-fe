package com.example.arten.model.network

import android.content.Context
import android.content.SharedPreferences

class PrefManager(var context: Context) {
    private val privateMode = 0
    
    private val prefName  = "SharedPref"
    private val isLogin = "IsLoggedIn"
    
    private var pref:SharedPreferences = context.getSharedPreferences(prefName, privateMode)
    private var editor:SharedPreferences.Editor = pref.edit()
    
    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(isLogin, isLoggedIn)
        editor.commit()
    }
    
    fun setUsername(username: String) {
        editor.putString("username", username)
        editor.commit()
    }
    
    fun setToken(token: String) {
        editor.putString("jwtToken", token)
        editor.commit()
    }
    
    fun setEmail(email: String) {
        editor.putString("email", email)
        editor.commit()
    }
    
    fun isLogin(): Boolean {
        return pref.getBoolean(isLogin, false)
    }
    
    fun getUsername(): String? {
        return pref.getString("username", null)
    }
    
    fun getToken(): String? {
        return pref.getString("token", null)
    }
    
    fun getEmail(): String? {
        return pref.getString("email", null)
    }
    
    fun clear() {
        editor.clear()
        editor.commit()
    }
}