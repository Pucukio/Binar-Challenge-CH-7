package com.pucuk.binar_challenge_ch_7.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.pucuk.binar_challenge_ch_7.R
import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteEntity
import com.pucuk.binar_challenge_ch_7.data.model.ResponseDetailFilm
import com.pucuk.binar_challenge_ch_7.databinding.FragmentDetailBinding
import com.pucuk.binar_challenge_ch_7.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private var isFavorite by Delegates.notNull<Boolean>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailMovie(arguments?.getInt("MOVIE_ID")!!)
        viewModel.session()
        bind()
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.favoriteFab.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.favoriteFab.setImageResource(R.drawable.ic_favorite_border)
            }
            isFavorite = it
        }
    }

    private fun selectFavorite(detailMovieResponse: ResponseDetailFilm) {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                viewModel.isFavorite(detailMovieResponse.id, user.uid)
                binding.favoriteFab.setOnClickListener {
                    if (!isFavorite) {
                        viewModel.addFavorite(
                            FavoriteEntity(
                                id_movie = detailMovieResponse.id,
                                photo = detailMovieResponse.posterPath,
                                title = detailMovieResponse.title,
                                uuid_user = user.uid
                            )
                        )
                        binding.favoriteFab.setImageResource(R.drawable.ic_favorite)
                        isFavorite = true
                    } else {
                        viewModel.deleteFavorite(detailMovieResponse.id, user.uid)
                        binding.favoriteFab.setImageResource(R.drawable.ic_favorite_border)
                        isFavorite = false
                    }
                }
            }
        }

    }

    private fun bind() {
        viewModel.loadingState.observe(viewLifecycleOwner) {
            binding.pb.isVisible = it
            binding.tvTitle.isVisible = !it
            binding.ivPhoto.isVisible = !it
            binding.tvDate.isVisible = !it
            binding.tvGenre.isVisible = !it
            binding.tvLanguage.isVisible = !it
            binding.ivBackdrop.isVisible = !it
            binding.tvNameDescription.isVisible = !it
        }
        viewModel.movie.observe(viewLifecycleOwner) {
            val imageUrl = "https://image.tmdb.org/t/p/w500"
            Glide.with(requireContext()).load(imageUrl + it.backdropPath).into(binding.ivBackdrop)
            Glide.with(requireContext()).load(imageUrl + it.posterPath).into(binding.ivPhoto)
            binding.tvTitle.text = it.title
            binding.topAppBar.title = it.title
            binding.tvDate.text = it.releaseDate
            binding.tvGenre.text = it.genres.joinToString(", ") { genre -> genre.name}
            binding.tvLanguage.text = it.spokenLanguages.joinToString(", ") { spokenLanguage -> spokenLanguage.name }
            binding.tvNameDescription.text = it.overview
            selectFavorite(it)
        }
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}