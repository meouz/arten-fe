package com.example.arten.ui.translate

import android.media.MediaRecorder
import android.widget.TextView
import androidx.lifecycle.ViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TranslateViewModel : ViewModel() {
    companion object {
        lateinit var dirPathMediaRecorder: String
    }
    
    var isRecording = false
    var permissionGranted = false
    private lateinit var filename: String
    private lateinit var recorder: MediaRecorder
    var language: String = "English"
    var languageResult: String = "Indonesia"
    
    private fun prepareRecorder() {
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
    
    fun startRecording() {
        try {
            prepareRecorder()
            isRecording = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun stopRecording() {
        isRecording = false
        recorder.stop()
        // send record to server
    }
    
    fun resetRecording() {
        try {
            recorder.reset()
            isRecording = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun switchLanguage(tvLanguage: TextView, tvLanguageResult: TextView) {
        val temp: String = this.language
        this.language = this.languageResult
        this.languageResult = temp
        tvLanguage.text = this.language
        tvLanguageResult.text = this.languageResult
    }
    
    fun setDirPathMediaRecorder(dirPath: String) {
        dirPathMediaRecorder = dirPath
    }
}