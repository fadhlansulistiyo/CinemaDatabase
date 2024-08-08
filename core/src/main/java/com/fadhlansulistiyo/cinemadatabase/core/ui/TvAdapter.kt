package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadhlansulistiyo.core.R
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.core.utils.toVoteAverageFormat
import com.fadhlansulistiyo.core.databinding.ItemTvBinding

class TvAdapter(private val onItemClick: (Int) -> Unit) : ListAdapter<Tv, TvAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val tv = getItem(position)
        holder.bind(tv, onItemClick)
    }

    class ListViewHolder(private val binding: ItemTvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: Tv, onItemClick: (Int) -> Unit) {
            binding.itemNameTv.text = tv.name
            binding.itemRatingTv.text = tv.voteAverage.toVoteAverageFormat(1)
            Glide.with(itemView.context)
                .load(IMAGE_URL + tv.posterPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp).error(R.drawable.ic_error)
                )
                .into(binding.itemPosterPathTv)

            itemView.setOnClickListener {
                onItemClick(tv.id)
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tv>() {
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }
        }
    }
}