package com.example.arten.ui.translate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arten.R
import com.example.arten.adapter.RVTranslateAdapter
import com.example.arten.databinding.FragmentTranslateBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TranslateFragment : Fragment() {
    
    private var _binding: FragmentTranslateBinding? = null
    private val binding get() = _binding!!
    private val translateActivity: TranslateActivity = TranslateActivity()
    private var isRecording = false
    private var isPaused = false
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTranslateBinding.inflate(inflater, container, false)
        
        val fabRecord: FloatingActionButton = binding.fabRecord
        
        fabRecord.setOnClickListener {
            when {
                isPaused -> {
                    translateActivity.resumeRecording()
                    isPaused = false
                    isRecording = true
                    fabRecord.setImageResource(R.drawable.ic_resume)
                }
                
                isRecording -> {
                    translateActivity.pauseRecording()
                    isPaused = true
                    isRecording = false
                    fabRecord.setImageResource(R.drawable.ic_pause)
                }
                
                else -> {
                    try {
                        translateActivity.startRecording()
                        isRecording = true
                        fabRecord.setImageResource(R.drawable.ic_resume)
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val dummy = listOf(
            "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December",
            "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December",
        ).toList()
        
        val customAdapter = RVTranslateAdapter(dummy)
        
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = customAdapter
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}