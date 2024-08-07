package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL_ORIGINAL
import com.fadhlansulistiyo.cinemadatabase.databinding.ItemNowPlayingBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailMovieActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toVoteAverageFormat

class NowPlayingAdapter : ListAdapter<Movie, NowPlayingAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemNowPlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class ListViewHolder(private val binding: ItemNowPlayingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.itemTitleNowPlaying.text = movie.title
            binding.tvItemRatingNowPlaying.text = movie.voteAverage.toVoteAverageFormat(1)
            Glide.with(itemView.context)
                .load(IMAGE_URL_ORIGINAL + movie.backdropPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                        .error(R.drawable.ic_error)
                )
                .into(binding.itemPosterNowPlaying)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, movie.id)
                itemView.context.startActivity(intent)
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