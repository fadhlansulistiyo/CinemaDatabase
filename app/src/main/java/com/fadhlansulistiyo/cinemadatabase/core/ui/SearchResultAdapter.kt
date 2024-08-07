package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiSearch
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.databinding.ItemSearchBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toFormattedDateString
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toVoteAverageFormat

class SearchResultAdapter(
    private val onItemClicked: (MultiSearch) -> Unit
) : PagingDataAdapter<MultiSearch, SearchResultAdapter.SearchResultViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    class SearchResultViewHolder(
        private val binding: ItemSearchBinding,
        private val onItemClicked: (MultiSearch) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = (bindingAdapter as SearchResultAdapter).getItem(position)
                    item?.let { onItemClicked(it) }
                }
            }
        }

        fun bind(searchResult: MultiSearch) {
            with(binding) {
                itemName.text = searchResult.title
                itemAirDate.text = searchResult.releaseDate.toFormattedDateString()
                itemVoteAverage.text = searchResult.voteAverage.toVoteAverageFormat(1)
                itemOverview.text = searchResult.overview
                Glide.with(itemView.context)
                    .load(IMAGE_URL + searchResult.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                            .error(R.drawable.ic_error)
                    )
                    .into(itemPosterPath)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MultiSearch>() {
            override fun areItemsTheSame(oldItem: MultiSearch, newItem: MultiSearch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MultiSearch, newItem: MultiSearch): Boolean {
                return oldItem == newItem
            }
        }
    }
}
