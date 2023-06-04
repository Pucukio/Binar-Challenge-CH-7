package com.pucuk.binar_challenge_ch_7.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pucuk.binar_challenge_ch_7.R
import com.pucuk.binar_challenge_ch_7.databinding.FragmentRegisterBinding
import com.pucuk.binar_challenge_ch_7.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.tvLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        _binding?.btnRegister?.setOnClickListener {
            val email = _binding?.etEmail?.text.toString()
            val password = _binding?.etPassword?.text.toString()

            if (email.isEmpty()) {
                _binding?.etEmail?.error = "Email must not be empty"
                _binding?.etEmail?.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _binding?.etEmail?.error = "Invalid email format"
                _binding?.etEmail?.requestFocus()
            } else if (password.isEmpty()) {
                _binding?.etPassword?.error = "Password must not be empty"
                _binding?.etPassword?.requestFocus()
            } else {
                viewModel.registerFirebase(email, password)
            }
        }

        viewModel.register.observe(viewLifecycleOwner) { result ->
            if (result.equals("Registration successful!") ) {
                Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}