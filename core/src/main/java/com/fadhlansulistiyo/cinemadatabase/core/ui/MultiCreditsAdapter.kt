package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiCreditsMovieTv
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.core.utils.toVoteAverageFormat
import com.fadhlansulistiyo.core.R
import com.fadhlansulistiyo.core.databinding.ItemTvBinding

class MultiCreditsAdapter(private val onItemClicked: (MultiCreditsMovieTv) -> Unit) :
    ListAdapter<MultiCreditsMovieTv, MultiCreditsAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val credits = getItem(position)
        credits?.let { holder.bind(it) }
    }

    class ListViewHolder(
        private val binding: ItemTvBinding,
        private val onItemClicked: (MultiCreditsMovieTv) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = (bindingAdapter as MultiCreditsAdapter).getItem(position)
                    item?.let { onItemClicked(it) }
                }
            }
        }

        fun bind(credits: MultiCreditsMovieTv) {
            with(binding) {
                itemNameTv.text = credits.title
                itemRatingTv.text = credits.voteAverage.toVoteAverageFormat(1)
                Glide.with(itemView.context)
                    .load(IMAGE_URL + credits.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                            .error(R.drawable.ic_error)
                    )
                    .into(itemPosterPathTv)
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