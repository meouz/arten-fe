package com.example.arten.ui.translate.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.arten.R
import com.example.arten.databinding.FragmentRecordBinding
import com.example.arten.ui.translate.TranslateActivity

class RecordFragment : Fragment() {
    
    private lateinit var binding: FragmentRecordBinding
    private val translateActivity: TranslateActivity = TranslateActivity()
    private var isRecording = false
    private var isPaused = false
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        
        val btnRecord = binding.btnRecord
        btnRecord.setOnClickListener {
            when {
                isPaused -> {
                    translateActivity.resumeRecording()
                    isPaused = false
                    isRecording = true
                    btnRecord.setImageResource(R.drawable.ic_resume)
                    message("Resume")
                }
                
                isRecording -> {
                    translateActivity.pauseRecording()
                    isPaused = true
                    isRecording = false
                    btnRecord.setImageResource(R.drawable.ic_pause)
                    message("Pause")
                }
                
                else -> {
                    try {
                        translateActivity.startRecording()
                        isRecording = true
                        btnRecord.setImageResource(R.drawable.ic_resume)
                        message("Recording")
                    } catch (e: Exception) {
                        message(e.message.toString())
                    }
                }
            }
        }
        
        val btnDelete = binding.btnDelete
        btnDelete.setOnClickListener {
            translateActivity.resetRecording()
            isRecording = false
            isPaused = false
            btnRecord.setImageResource(R.drawable.ic_record)
            message("Delete")
        }
        
        val btnSend = binding.btnSend
        btnSend.setOnClickListener {
            // send the recording
            isRecording = false
            isPaused = false
            btnRecord.setImageResource(R.drawable.ic_record)
            message("Send")
        }
        
        return binding.root
    }
    
    private fun message(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}