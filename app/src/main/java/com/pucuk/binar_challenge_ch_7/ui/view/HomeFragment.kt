package com.pucuk.binar_challenge_ch_7.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pucuk.binar_challenge_ch_7.R
import com.pucuk.binar_challenge_ch_7.databinding.FragmentHomeBinding
import com.pucuk.binar_challenge_ch_7.ui.adapter.HomeAdapter
import com.pucuk.binar_challenge_ch_7.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var _binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.session()
        viewModel.getFilm()
        viewModel.movie.observe(viewLifecycleOwner) {
            _binding.rvFilm.apply {
                adapter = HomeAdapter(it.results)
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.user.observe(viewLifecycleOwner) {
            _binding.tvWelcome.text = "Hi, ${it?.email}"
        }
        _binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorite -> {
                    findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
                    true
                }
                R.id.about -> {
                    findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                    true
                }
                R.id.logout -> {
                    Firebase.auth.signOut()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}