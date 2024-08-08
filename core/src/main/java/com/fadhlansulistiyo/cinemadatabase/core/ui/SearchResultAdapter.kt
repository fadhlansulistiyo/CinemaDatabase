package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiSearch
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.core.utils.toFormattedDateString
import com.fadhlansulistiyo.cinemadatabase.core.utils.toVoteAverageFormat
import com.fadhlansulistiyo.core.R
import com.fadhlansulistiyo.core.databinding.ItemSearchBinding

class SearchResultAdapter(
    private val onItemClicked: (MultiSearch) -> Unit
) : PagingDataAdapter<MultiSearch, SearchResultAdapter.SearchResultViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it, onItemClicked) }
    }

    class SearchResultViewHolder(
        private val binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(searchResult: MultiSearch, onItemClicked: (MultiSearch) -> Unit) {
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

                itemView.setOnClickListener {
                    onItemClicked(searchResult.copy())
                }
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
