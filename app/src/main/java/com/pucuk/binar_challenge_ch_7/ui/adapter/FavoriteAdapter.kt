package com.pucuk.binar_challenge_ch_7.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pucuk.binar_challenge_ch_7.R
import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteEntity
import com.pucuk.binar_challenge_ch_7.databinding.ItemFavBinding
import com.pucuk.binar_challenge_ch_7.databinding.ItemListBinding


class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<FavoriteEntity>() {
        override fun areItemsTheSame(
            oldItem: FavoriteEntity,
            newItem: FavoriteEntity
        ): Boolean =
            oldItem.id_movie == newItem.id_movie

        override fun areContentsTheSame(
            oldItem: FavoriteEntity,
            newItem: FavoriteEntity
        ): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEntity: FavoriteEntity) {
            val imageUrl = "https://image.tmdb.org/t/p/w500"
            binding.tvTitle.text = favoriteEntity.title
            Glide.with(itemView).load(imageUrl + favoriteEntity.photo).into(binding.ivImg)
            binding.root.setOnClickListener {
                it.findNavController()
                    .navigate(
                        R.id.action_favoriteFragment_to_detailFragment,
                        Bundle().apply { putInt("MOVIE_ID", favoriteEntity.id_movie) })
            }
        }
    }
}