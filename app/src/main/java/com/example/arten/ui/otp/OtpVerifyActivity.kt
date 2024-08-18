package com.example.arten.ui.otp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.arten.R
import com.example.arten.model.network.RClient
import com.example.arten.model.model.otp.OTPActivateRequest
import com.example.arten.model.model.otp.OTPActivateResponse
import com.example.arten.model.model.otp.OTPActivateResponseData
import com.example.arten.model.model.otp.OTPRequest
import com.example.arten.model.model.otp.OTPResponse
import com.example.arten.model.network.PrefManager
import com.example.arten.ui.translate.TranslateActivity
import com.google.gson.Gson
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
        otpSentTo.append(prefManager.getEmail())
        
        val resendOtp = findViewById<TextView>(R.id.resendOtp)
        resendOtp.setOnClickListener {
            resendOtp()
        }
        
        val verifyOtp = findViewById<TextView>(R.id.verifyOtp)
        verifyOtp.setOnClickListener {
            verifyOtp()
        }
    }
    
    fun resendOtp() {
        // Resend the OTP code
        val username = prefManager.getUsername()
        
        username?.let {
            RClient.instance.sendOtp(OTPRequest(username))
                .enqueue(object : Callback<OTPResponse> {
                    override fun onResponse(
                        call: Call<OTPResponse>,
                        response: Response<OTPResponse>,
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
                    
                    override fun onFailure(call: Call<OTPResponse>, t: Throwable) {
                        Toast.makeText(
                            this@OtpVerifyActivity,
                            "Failed to send OTP code\nTry again later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
    
    fun verifyOtp() {
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
                            val body = response.body()
                            
                            body?.let {
                                if (body.data is Map<*, *>) {
                                    // OTP code verified successfully
                                    val gson = Gson()
                                    val jsonData = gson.toJsonTree(it.data).asJsonObject
                                    val responseData =
                                        gson.fromJson(jsonData, OTPActivateResponseData::class.java)
                                    
                                    // Save the token to the shared preferences
                                    prefManager.setToken(responseData.token)
                                    
                                    // Redirect to the home screen
                                    val intent =
                                        Intent(
                                            this@OtpVerifyActivity,
                                            TranslateActivity::class.java
                                        )
                                    startActivity(intent)
                                } else {
                                    TODO("Not yet implemented")
                                }
                            }
                        } else {
                            // OTP code verification failed
                            TODO("Not yet implemented")
                        }
                    }
                    
                    override fun onFailure(call: Call<OTPActivateResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }
        
    }
}