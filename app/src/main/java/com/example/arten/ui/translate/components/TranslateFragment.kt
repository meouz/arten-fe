package com.example.arten.ui.translate.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.arten.databinding.FragmentTranslateBinding
import com.example.arten.ui.translate.TranslateViewModel

class TranslateFragment : Fragment() {
    
    private var _binding: FragmentTranslateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TranslateViewModel = TranslateViewModel()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTranslateBinding.inflate(inflater, container, false)
        
        val tvTranslate = binding.tvTranslate
        tvTranslate.text = ""
        tvTranslate.text = viewModel.result
        
        val btnHistory = binding.btnHistory
        btnHistory.setOnClickListener {
            viewModel.checkHistory()
        }
        
        return binding.root
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}