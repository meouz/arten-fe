package com.example.arten.ui.translate

import android.media.MediaRecorder
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.arten.domain.repository.TranslateRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TranslateViewModel : ViewModel() {
    companion object {
        lateinit var dirPathMediaRecorder: String
    }
    
    private val repository = TranslateRepository()
    var isRecording = false
    var permissionGranted = false
    private lateinit var filename: String
    private lateinit var recorder: MediaRecorder
    var result: String = ""
    var originLanguage: String = "English"
    var languageResult: String = "Indonesia"
    var token: String = ""
    
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
        
        val file: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file", filename, RequestBody.create(
                MediaType.parse("audio/mp3"), File("$dirPathMediaRecorder/$filename.mp3")
            )
        )
        
        val fileSize = File("$dirPathMediaRecorder/$filename.mp3").length()
        
        Log.d(
            "AudioFileSize",
            ((fileSize / 1024).toString() + "." + (fileSize % 1024).toString() + "kb")
        )
        
        result = repository.translateText(
            originLanguage,
            languageResult,
            repository.transcribeVoice(file, originLanguage)
        )
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
        val temp: String = this.originLanguage
        this.originLanguage = this.languageResult
        this.languageResult = temp
        tvLanguage.text = this.originLanguage
        tvLanguageResult.text = this.languageResult
    }
    
    fun setDirPathMediaRecorder(dirPath: String) {
        dirPathMediaRecorder = dirPath
    }
    
    fun checkHistory() {
        Log.d("token", token)
        repository.history(token)
    }
}