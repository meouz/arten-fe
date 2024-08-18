package com.example.arten.ui.otp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.R
import com.example.arten.model.network.RClient
import com.example.arten.model.model.otp.OTPRequest
import com.example.arten.model.model.otp.OTPResponse
import com.example.arten.model.network.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpSendActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_send)
    }
    
    fun getOtpCode(view: View) {
        // Get the OTP code from the server
        val username = PrefManager(this@OtpSendActivity).getUsername()
        
        username?.let {
            RClient.instance.sendOtp(OTPRequest(username)).enqueue(object : Callback<OTPResponse> {
                override fun onResponse(call: Call<OTPResponse>, response: Response<OTPResponse>) {
                    if (response.isSuccessful) {
                        // OTP code sent successfully
                        
                        // Redirect to the OTP verification screen
                        val intent = Intent(this@OtpSendActivity, OtpVerifyActivity::class.java)
                        startActivity(intent)
                    } else {
                        // OTP code sending failed
                    }
                    TODO("Not yet implemented")
                }
                
                override fun onFailure(call: Call<OTPResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}