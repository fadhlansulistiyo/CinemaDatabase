package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.core.utils.toVoteAverageFormat
import com.fadhlansulistiyo.core.R
import com.fadhlansulistiyo.core.databinding.ItemWatchlistBinding

class WatchlistTvAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<WatchlistTv, WatchlistTvAdapter.MyViewHolder>(DIFF_CALLBACK) {

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
        fun bind(tv: WatchlistTv, onItemClick: (Int) -> Unit) {
            binding.itemTitle.text = tv.name
            binding.itemRatingTv.text = tv.voteAverage.toVoteAverageFormat(1)
            Glide.with(itemView.context)
                .load(IMAGE_URL + tv.posterPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                        .error(R.drawable.ic_error)
                )
                .into(binding.itemPosterPath)

            itemView.setOnClickListener {
                onItemClick(tv.id)
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