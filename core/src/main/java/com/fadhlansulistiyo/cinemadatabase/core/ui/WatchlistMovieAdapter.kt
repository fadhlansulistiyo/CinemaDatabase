package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import com.fadhlansulistiyo.cinemadatabase.core.utils.loadImage
import com.fadhlansulistiyo.cinemadatabase.core.utils.toVoteAverageFormat
import com.fadhlansulistiyo.core.databinding.ItemWatchlistBinding

class WatchlistMovieAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<WatchlistMovie, WatchlistMovieAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemWatchlistBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }

    class MyViewHolder(private val binding: ItemWatchlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: WatchlistMovie, onItemClick: (Int) -> Unit) {
            with(binding) {
                itemTitle.text = movie.title
                itemRatingTv.text = movie.voteAverage.toVoteAverageFormat(1)
                itemPosterPath.loadImage(itemView.context, movie.posterPath)
            }

            itemView.setOnClickListener {
                onItemClick(movie.id)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WatchlistMovie>() {
            override fun areItemsTheSame(
                oldItem: WatchlistMovie,
                newItem: WatchlistMovie
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: WatchlistMovie,
                newItem: WatchlistMovie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}