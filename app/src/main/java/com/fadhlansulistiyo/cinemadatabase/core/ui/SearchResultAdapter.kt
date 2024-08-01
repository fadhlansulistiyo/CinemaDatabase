package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiSearch

class SearchResultAdapter : PagingDataAdapter<MultiSearch, SearchResultAdapter.SearchResultViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val releaseDateTextView: TextView = itemView.findViewById(R.id.releaseDateTextView)
        private val ratingTextView: TextView = itemView.findViewById(R.id.ratingTextView)

        fun bind(searchResult: MultiSearch) {
            titleTextView.text = searchResult.title ?: searchResult.name ?: "Unknown"
            releaseDateTextView.text = "Release Date: ${searchResult.releaseDate ?: "Unknown"}"
            ratingTextView.text = "Rating: ${searchResult.voteAverage ?: "N/A"}"
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500/${searchResult.posterPath}")
                .into(posterImageView)
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
