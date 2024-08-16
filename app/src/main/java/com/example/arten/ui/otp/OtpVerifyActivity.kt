package com.example.arten.ui.otp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.R
import com.example.arten.model.model.auth.RClient
import com.example.arten.model.model.auth.data.OTPActivateRequest
import com.example.arten.model.model.auth.data.OTPActivateResponse
import com.example.arten.model.model.auth.data.OTPRequest
import com.example.arten.model.model.auth.data.OTPResponse
import com.example.arten.model.model.pref.PrefManager
import com.example.arten.ui.translate.TranslateActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpVerifyActivity : AppCompatActivity() {
    private lateinit var prefManager: PrefManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verify)
        
        prefManager = PrefManager(this)
    }
    
    fun resendOtp(view: View) {
        // Resend the OTP code
        val username = prefManager.getUsername()
        username?.let {
            RClient.instance.sendOtp(OTPRequest(username))
                .enqueue(object : Callback<OTPResponse> {
                    override fun onResponse(call: Call<OTPResponse>, response: Response<OTPResponse>) {
                        if (response.isSuccessful) {
                            // OTP code sent successfully
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
    
    fun verifyOtp(view: View) {
        val username = PrefManager(this).getUsername()
        val otp =
            R.id.otp1.toString() + R.id.otp2.toString() + R.id.otp3.toString() + R.id.otp4.toString()
        
        // Verify the OTP code
        username?.let {
            RClient.instance.activateOtp(OTPActivateRequest(username, otp))
                .enqueue(object : Callback<OTPActivateResponse> {
                    override fun onResponse(
                        call: Call<OTPActivateResponse>,
                        response: Response<OTPActivateResponse>,
                    ) {
                        if (response.isSuccessful) {
                            // OTP code verified successfully
                            
                            // Redirect to the home screen
                            val intent = Intent(this@OtpVerifyActivity, TranslateActivity::class.java)
                            startActivity(intent)
                        } else {
                            // OTP code verification failed
                        }
                        TODO("Not yet implemented")
                    }
                    
                    override fun onFailure(call: Call<OTPActivateResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }
        
    }
}