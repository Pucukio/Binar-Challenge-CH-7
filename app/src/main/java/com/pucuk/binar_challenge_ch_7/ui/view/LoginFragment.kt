package com.pucuk.binar_challenge_ch_7.ui.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pucuk.binar_challenge_ch_7.R
import com.pucuk.binar_challenge_ch_7.databinding.FragmentLoginBinding
import com.pucuk.binar_challenge_ch_7.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var _binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.session()
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
        _binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        _binding.tvLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        _binding.btnLogin.setOnClickListener {
            val email = _binding.etEmail.text.toString()
            val password = _binding.etPassword.text.toString()

            if (email.isEmpty()) {
                _binding.tiEmail.error = "Email must be filled"
                _binding.tiEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _binding.tiEmail.error = "Invalid email format"
                _binding.tiEmail.requestFocus()
            } else if (password.isEmpty()) {
                _binding.tiPassword.error = "Password must be filled"
                _binding.tiPassword.requestFocus()
            } else {
                viewModel.loginFirebase(email, password)
            }
        }
        viewModel.login.observe(viewLifecycleOwner) { result ->
            if (result.equals("Login Success!", true)) {
                Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
            }
        }
    }
}