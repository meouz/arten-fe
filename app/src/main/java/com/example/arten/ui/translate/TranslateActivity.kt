package com.example.arten.ui.translate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.arten.R
import com.example.arten.utils.PrefManager

const val REQUEST_CODE = 200

class TranslateActivity : AppCompatActivity() {
    
    private lateinit var activity: TranslateActivity
    private val viewModel: TranslateViewModel = TranslateViewModel()
    private var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private lateinit var prefManager: PrefManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        
        activity = this
        
        prefManager = PrefManager(this)
        Log.d("TranslateActivity", "Token: ${prefManager.getToken()}")
        Log.d("TranslateActivity", "Email: ${prefManager.getEmail()}")
        Log.d("TranslateActivity", "Username: ${prefManager.getUsername()}")
        Log.d("TranslateActivity", "Logged: ${prefManager.isLogin()}")
        Log.d("TranslateActivity", "Verified: ${prefManager.isVerified()}")
        
        viewModel.token = prefManager.getToken().toString()
        
        viewModel.setDirPathMediaRecorder(getExternalFilesDir(null)?.absolutePath.toString())
        
        viewModel.permissionGranted =
            ActivityCompat.checkSelfPermission(activity, permissions[0]) ==
                    PackageManager.PERMISSION_GRANTED
        
        if (!viewModel.permissionGranted)
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE)
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == REQUEST_CODE)
            viewModel.permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
    }
}