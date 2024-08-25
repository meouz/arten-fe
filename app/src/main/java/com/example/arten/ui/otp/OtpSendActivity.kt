package com.example.arten.ui.otp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.R
import com.example.arten.data.ResponseData
import com.example.arten.data.otp.OTPRequest
import com.example.arten.utils.PrefManager
import com.example.arten.utils.RClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpSendActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_send)
    }
    
    fun getOtpCode(view: View) {
        val username = PrefManager(this@OtpSendActivity).getUsername()
        
        Log.d("OtpSendActivity", "Username: $username")
        Log.d("OtpSendActivity", "PrefManager: ${PrefManager(this@OtpSendActivity).getEmail()}")
        
        username?.let {
            RClient.instance.sendOtp(OTPRequest(username)).enqueue(object : Callback<ResponseData> {
                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    if (response.isSuccessful) {
                        Log.d("OtpSendActivity", "onResponse: ${response.body()}")
                        Toast.makeText(this@OtpSendActivity, "OTP code sent successfully", Toast.LENGTH_SHORT).show()
                        
                        finish()
                        startActivity(Intent(this@OtpSendActivity, OtpVerifyActivity::class.java))
                    } else {
                        Log.d("OtpSendActivity", "onCall: ${call.request()}")
                        Log.d("OtpSendActivity", "onResponse: ${response.body()}")
                        Toast.makeText(this@OtpSendActivity, "OTP code sending failed", Toast.LENGTH_SHORT).show()
                    }
                }
                
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.d("OtpSendActivity", "onFailure: ${t.message}")
                    Toast.makeText(this@OtpSendActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}