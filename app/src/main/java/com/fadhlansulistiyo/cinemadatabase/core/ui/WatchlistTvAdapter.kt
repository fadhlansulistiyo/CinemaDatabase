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
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.databinding.ItemWatchlistBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailMovieActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailTvActivity

class WatchlistTvAdapter :
    ListAdapter<WatchlistTv, WatchlistTvAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemWatchlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemWatchlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: WatchlistTv) {
            binding.tvName.text = tv.name
            binding.tvReleaseDate.text = tv.firstAirDate
            binding.tvRating.text = tv.voteAverage.toString()
            Glide.with(itemView.context)
                .load(IMAGE_URL + tv.posterPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp).error(R.drawable.ic_error)
                )
                .into(binding.ivPoster)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailTvActivity::class.java)
                intent.putExtra(DetailTvActivity.EXTRA_TV_ID, tv.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WatchlistTv>() {
            override fun areItemsTheSame(
                oldItem: WatchlistTv,
                newItem: WatchlistTv
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: WatchlistTv,
                newItem: WatchlistTv
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}