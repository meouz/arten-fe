package com.example.arten.ui.translate

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.arten.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val REQUEST_CODE = 200

class TranslateActivity : AppCompatActivity() {
    
    companion object {
        private var permissionGranted = false
        private var dirPath = ""
    }
    
    private var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private lateinit var dirPathMediaRecorder: String
    private lateinit var filename: String
    private lateinit var recorder: MediaRecorder
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        dirPath = getExternalFilesDir(null)?.absolutePath.toString()
        
        permissionGranted = ActivityCompat.checkSelfPermission(this, permissions[0]) ==
                PackageManager.PERMISSION_GRANTED
        
        if (!permissionGranted)
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == REQUEST_CODE)
            permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
    }
    
    fun startRecording() {
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
            println("No permission granted")
            return
        }
        println("Start recording")
        // start recording
        
        dirPathMediaRecorder = "$dirPath/"
        val file = File(dirPathMediaRecorder)
        if (!file.exists()) {
            file.mkdirs()
            println("create directory")
        }
        
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        filename = "audio_${dateFormat.format(Date())}"
        println("File will be saved in: $dirPathMediaRecorder/$filename.mp3")
        
        recorder = MediaRecorder()
        
        println("set audio source")
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$dirPathMediaRecorder/$filename.mp3")
            
            try {
                prepare()
                start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        println("success")
    }
    
    fun pauseRecording() {
        recorder.pause()
        println("success")
    }
    
    fun resumeRecording() {
        recorder.resume()
        println("success")
    }
}