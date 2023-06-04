package com.pucuk.binar_challenge_ch_7.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.pucuk.binar_challenge_ch_7.R
import com.pucuk.binar_challenge_ch_7.databinding.FragmentProfileBinding
import com.pucuk.binar_challenge_ch_7.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private  val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.session()
        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                etEmail.setText(it?.email.toString())
            }
        }

        binding.apply {
            btnUpdate.setOnClickListener {
                val email = _binding?.etEmail?.text.toString().trim()
                val username = _binding?.etUsername?.text.toString().trim()
                if (username.length > 12) {
                    FirebaseCrashlytics.getInstance().log("Exploiting Username")
                    throw RuntimeException("Application Crashed due to Exploiting Username")
                } else {
                    viewModel.updateEmail(email)
                    viewModel.update.observe(viewLifecycleOwner) {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.profileFragment)
                    }
                }
            }
            btnLogout.setOnClickListener {
                Firebase.auth.signOut()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
            backButton.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}