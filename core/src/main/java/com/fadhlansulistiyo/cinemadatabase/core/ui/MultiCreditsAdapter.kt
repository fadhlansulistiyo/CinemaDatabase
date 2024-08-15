package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiCreditsMovieTv
import com.fadhlansulistiyo.cinemadatabase.core.utils.loadImage
import com.fadhlansulistiyo.cinemadatabase.core.utils.toVoteAverageFormat
import com.fadhlansulistiyo.core.databinding.ItemTvBinding

class MultiCreditsAdapter(private val onItemClicked: (MultiCreditsMovieTv) -> Unit) :
    ListAdapter<MultiCreditsMovieTv, MultiCreditsAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val credits = getItem(position)
        credits?.let { holder.bind(it, onItemClicked) }
    }

    class ListViewHolder(
        private val binding: ItemTvBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(credits: MultiCreditsMovieTv, onItemClicked: (MultiCreditsMovieTv) -> Unit) {
            with(binding) {
                itemNameTv.text = credits.title
                itemRatingTv.text = credits.voteAverage.toVoteAverageFormat(1)
                itemPosterPathTv.loadImage(itemView.context, credits.posterPath)
            }

            itemView.setOnClickListener {
                onItemClicked(credits.copy())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MultiCreditsMovieTv>() {
            override fun areItemsTheSame(
                oldItem: MultiCreditsMovieTv,
                newItem: MultiCreditsMovieTv
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MultiCreditsMovieTv,
                newItem: MultiCreditsMovieTv
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}