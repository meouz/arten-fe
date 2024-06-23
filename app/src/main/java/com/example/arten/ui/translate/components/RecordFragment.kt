package com.example.arten.ui.translate.components

import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.arten.R
import com.example.arten.databinding.FragmentRecordBinding
import com.example.arten.ui.translate.TranslateActivity

class RecordFragment : Fragment(), Timer.OnTimerTickListener {
    
    private lateinit var binding: FragmentRecordBinding
    private val translateActivity: TranslateActivity = TranslateActivity()
    private var isRecording = false
    private var isPaused = false
    
    private lateinit var timer: Timer
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        
        timer = Timer(this)
        
        val btnRecord = binding.btnRecord
        btnRecord.setOnClickListener {
            when {
                isPaused -> {
                    translateActivity.resumeRecording()
                    isPaused = false
                    btnRecord.setImageResource(R.drawable.ic_pause)
                    timer.start(100)
                }
                
                isRecording -> {
                    translateActivity.pauseRecording()
                    isPaused = true
                    btnRecord.setImageResource(R.drawable.ic_resume)
                    timer.pause()
                }
                
                else -> {
                    try {
                        translateActivity.startRecording()
                        isRecording = true
                        btnRecord.setImageResource(R.drawable.ic_pause)
                        message("Recording")
                        timer.start(100)
                    } catch (e: Exception) {
                        message(e.message.toString())
                    }
                }
            }
        }
        
        val btnDelete = binding.btnDelete
        btnDelete.setOnClickListener {
            try {
                translateActivity.resetRecording()
                isRecording = false
                isPaused = false
                btnRecord.setImageResource(R.drawable.ic_record)
                message("Delete")
                timer.stop()
                onTick("00:00")
            } catch (e: Exception) {
                message(e.message.toString())
            }
        }
        
        val btnSend = binding.btnSend
        btnSend.setOnClickListener {
            try {
                isRecording = false
                isPaused = false
                btnRecord.setImageResource(R.drawable.ic_record)
                message("Send")
                timer.stop()
                onTick("00:00")
            } catch (e: Exception) {
                message(e.message.toString())
            }
        }
        
        return binding.root
    }
    
    private fun message(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    
    override fun onTick(time: String) {
        binding.tvTime.text = time
    }
}