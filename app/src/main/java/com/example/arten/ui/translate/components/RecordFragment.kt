package com.example.arten.ui.translate.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.arten.R
import com.example.arten.databinding.FragmentRecordBinding
import com.example.arten.ui.translate.TranslateViewModel

class RecordFragment : Fragment() {
    
    private lateinit var binding: FragmentRecordBinding
    private val viewModel: TranslateViewModel = TranslateViewModel()
    private lateinit var btnRecord: ImageButton
    private lateinit var btnCancel: ImageButton
    private lateinit var btnSwitchLanguage: ImageButton
    private lateinit var tvLanguage: TextView
    private lateinit var tvLanguageResult: TextView
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        
        tvLanguage = binding.tvLanguage
        tvLanguage.text = viewModel.language
        
        tvLanguageResult = binding.tvLanguageResult
        tvLanguageResult.text = viewModel.languageResult
        
        btnCancel = binding.btnCancel
        btnCancel.setOnClickListener {
            viewModel.resetRecording()
            btnRecord.setImageResource(R.drawable.btn_record)
            btnCancel.visibility = View.GONE
            message("Record canceled")
        }
        
        btnRecord = binding.btnRecord
        btnRecord.setOnClickListener {
            if (viewModel.isRecording) {
                viewModel.startRecording()
                btnRecord.setImageResource(R.drawable.btn_stop)
                btnCancel.visibility = View.VISIBLE
            } else {
                viewModel.stopRecording()
                btnRecord.setImageResource(R.drawable.btn_record)
                btnCancel.visibility = View.GONE
            }
        }
        
        btnSwitchLanguage = binding.btnSwitchLanguage
        btnSwitchLanguage.setOnClickListener {
            try {
                viewModel.switchLanguage(tvLanguage, tvLanguageResult)
            } catch (e: Exception) {
                message(e.message.toString())
            }
        }
        
        return binding.root
    }
    
    private fun message(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}