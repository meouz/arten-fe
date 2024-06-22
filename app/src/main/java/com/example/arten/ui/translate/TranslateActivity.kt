package com.example.arten.ui.translate

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentContainerView
import com.example.arten.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val REQUEST_CODE = 200

class TranslateActivity : AppCompatActivity() {
    
    companion object {
        private var permissionGranted = false
        private lateinit var activity: TranslateActivity
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
        activity = this
        
        permissionGranted = ActivityCompat.checkSelfPermission(activity, permissions[0]) ==
                PackageManager.PERMISSION_GRANTED
        
        if (!permissionGranted)
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE)
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
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE)
            throw Exception("Please grant permission to record audio.")
        }
        // start recording
        
        dirPathMediaRecorder = "$dirPath/"
        val file = File(dirPathMediaRecorder)
        if (!file.exists())
            file.mkdirs()
        
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        filename = "audio_${dateFormat.format(Date())}"
        println("File will be saved in: $dirPathMediaRecorder/$filename.mp3")
        
        recorder = MediaRecorder()
        
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
    }
    
    fun resetRecording() {
        recorder.reset()
    }
    
    fun pauseRecording() {
        recorder.pause()
    }
    
    fun resumeRecording() {
        recorder.resume()
    }
    
    fun stopRecording() {
        recorder.stop()
    }
    
    fun appear() {
        val fragmentCView =
            activity.findViewById<FragmentContainerView>(R.id.fragmentContainerView2)
        fragmentCView.visibility = View.VISIBLE
    }
    
    fun disappear() {
        val fragmentCView =
            activity.findViewById<FragmentContainerView>(R.id.fragmentContainerView2)
        fragmentCView.visibility = View.GONE
    }
}