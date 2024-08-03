package com.example.arten.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.arten.R
import com.example.arten.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding.btnRegister.setOnClickListener { register(it) }
        binding.btnLogin.setOnClickListener { backToLogin(it) }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    private fun register(view: View) {
        // Get the values from the EditText fields
        val etUsername = view.findViewById<View>(R.id.etUsername)
        val etEmail = view.findViewById<View>(R.id.etEmail)
        val etPassword = view.findViewById<View>(R.id.etPassword)
        val etConfirmPassword = view.findViewById<View>(R.id.etConfirmPassword)
        
        val username = etUsername.toString().trim()
        val email = etEmail.toString().trim()
        val password = etPassword.toString().trim()
        val confirmPassword = etConfirmPassword.toString().trim()
        
        // Call the register function from the ViewModel
        viewModel.register(username, email, password, confirmPassword)
    }
    
    private fun backToLogin(view: View) {
        // Navigate back to the LoginFragment
        val loginFragment = LoginFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, loginFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}