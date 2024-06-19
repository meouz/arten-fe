package com.example.arten.ui.translate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                    isPaused = false
                    isRecording = true
                    translateActivity.resumeRecording()
                    fabRecord.setImageResource(R.drawable.ic_resume)
                }
                
                isRecording -> {
                    isPaused = true
                    isRecording = false
                    translateActivity.pauseRecording()
                    fabRecord.setImageResource(R.drawable.ic_pause)
                }
                
                else -> {
                    isRecording = true
                    translateActivity.startRecording()
                    fabRecord.setImageResource(R.drawable.ic_resume)
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
            "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December",
        )
        
        val dataset = dummy.map { it }.toList()
        val customAdapter = RVTranslateAdapter(dataset)
        
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