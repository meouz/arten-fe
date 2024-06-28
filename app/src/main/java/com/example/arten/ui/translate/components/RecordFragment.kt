package com.example.arten.ui.translate.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.arten.R
import com.example.arten.databinding.FragmentRecordBinding
import com.example.arten.ui.translate.TranslateViewModel

class RecordFragment : Fragment(), Timer.OnTimerTickListener {
    
    private lateinit var binding: FragmentRecordBinding
    private lateinit var timer: Timer
    private val viewModel: TranslateViewModel = TranslateViewModel()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        
        timer = Timer(this)
        
        val tvLanguage = binding.tvLanguage
        val tvLanguageResult = binding.tvLanguageResult
        tvLanguage.text = viewModel.language
        tvLanguageResult.text = viewModel.languageResult
        
        val btnRecord = binding.btnRecord
        btnRecord.setOnClickListener {
            viewModel.startRecording()
            if (viewModel.isPaused) {
                btnRecord.setImageResource(R.drawable.ic_resume)
                timer.pause()
            } else if (viewModel.isRecording) {
                btnRecord.setImageResource(R.drawable.ic_pause)
                timer.start(100)
            }
        }
        
        val btnDelete = binding.btnDelete
        btnDelete.setOnClickListener {
            viewModel.resetRecording()
            btnRecord.setImageResource(R.drawable.ic_record)
            onTick("00:00")
            timer.stop()
            message("Delete")
        }
        
        val btnSend = binding.btnSend
        btnSend.setOnClickListener {
            viewModel.sendRecord()
            btnRecord.setImageResource(R.drawable.ic_record)
            onTick("00:00")
            timer.stop()
            message("Send")
        }
        
        val btnSwitchLanguage = binding.btnSwitchLanguage
        btnSwitchLanguage.setOnClickListener {
            try {
                viewModel.switchLanguage()
                tvLanguage.text = viewModel.language
                tvLanguageResult.text = viewModel.languageResult
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