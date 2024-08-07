package com.example.arten.ui.translate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.arten.R

const val REQUEST_CODE = 200

class TranslateActivity : AppCompatActivity() {
    
    private lateinit var activity: TranslateActivity
    private val viewModel: TranslateViewModel = TranslateViewModel()
    private var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        
        activity = this
        
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