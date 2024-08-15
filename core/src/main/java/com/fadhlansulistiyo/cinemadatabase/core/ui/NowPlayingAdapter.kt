package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.utils.loadImageOriginal
import com.fadhlansulistiyo.cinemadatabase.core.utils.toVoteAverageFormat
import com.fadhlansulistiyo.core.databinding.ItemNowPlayingBinding

class NowPlayingAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<Movie, NowPlayingAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemNowPlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, onItemClick)
    }

    class ListViewHolder(private val binding: ItemNowPlayingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onItemClick: (Int) -> Unit) {
            with(binding) {
                itemTitleNowPlaying.text = movie.title
                tvItemRatingNowPlaying.text = movie.voteAverage.toVoteAverageFormat(1)
                itemPosterNowPlaying.loadImageOriginal(itemView.context, movie.backdropPath)
            }

            itemView.setOnClickListener {
                onItemClick(movie.id)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}