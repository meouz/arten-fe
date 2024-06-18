package com.example.arten.ui.translate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arten.adapter.RVTranslateAdapter
import com.example.arten.databinding.FragmentTranslateBinding

class TranslateFragment : Fragment() {
    
    private var _binding: FragmentTranslateBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTranslateBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val dummy = listOf("January", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "February", "March",
            "February", "March", "February", "March", "February", "March", "April")
        
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