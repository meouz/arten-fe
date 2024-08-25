package com.example.arten.ui.otp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.R
import com.example.arten.data.ResponseData
import com.example.arten.data.otp.OTPActivateRequest
import com.example.arten.data.otp.OTPRequest
import com.example.arten.utils.PrefManager
import com.example.arten.utils.RClient
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
        
        val otpSentTo = findViewById<TextView>(R.id.otpSentTo)
        otpSentTo.append(" " + prefManager.getEmail())
        
        val resendOtp = findViewById<TextView>(R.id.resendOtp)
        resendOtp.setOnClickListener {
            resendOtp()
        }
        
        val verifyOtp = findViewById<TextView>(R.id.verifyOtp)
        verifyOtp.setOnClickListener {
            verifyOtp()
        }
    }
    
    private fun resendOtp() {
        // Resend the OTP code
        val username = prefManager.getUsername()
        
        username?.let {
            RClient.instance.sendOtp(OTPRequest(username))
                .enqueue(object : Callback<ResponseData> {
                    override fun onResponse(
                        call: Call<ResponseData>,
                        response: Response<ResponseData>,
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@OtpVerifyActivity,
                                "OTP code sent successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // OTP code sending failed
                            Toast.makeText(
                                this@OtpVerifyActivity,
                                "Failed to send OTP code\nTry again later",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    
                    override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                        Toast.makeText(
                            this@OtpVerifyActivity,
                            "Something went wrong\nTry again later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
    
    private fun verifyOtp() {
        val username = prefManager.getUsername()
        val otp1 = findViewById<TextView>(R.id.otp1).text.toString()
        val otp2 = findViewById<TextView>(R.id.otp2).text.toString()
        val otp3 = findViewById<TextView>(R.id.otp3).text.toString()
        val otp4 = findViewById<TextView>(R.id.otp4).text.toString()
        
        val otp = otp1 + otp2 + otp3 + otp4
        
        // Verify the OTP code
        username?.let {
            RClient.instance.activateOtp(OTPActivateRequest(username, otp))
                .enqueue(object : Callback<ResponseData> {
                    override fun onResponse(
                        call: Call<ResponseData>,
                        response: Response<ResponseData>,
                    ) {
                        if (response.isSuccessful) {
                            prefManager.setVerified(true)
                            
                            finish()
                            startActivity(
                                Intent(
                                    this@OtpVerifyActivity,
                                    TranslateActivity::class.java
                                )
                            )
                        } else {
                            // OTP code verification failed
                            Toast.makeText(
                                this@OtpVerifyActivity,
                                "Failed to verify OTP code\nTry again later",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    
                    override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                        Toast.makeText(
                            this@OtpVerifyActivity,
                            "Failed to verify OTP code\nTry again later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
        
    }
}