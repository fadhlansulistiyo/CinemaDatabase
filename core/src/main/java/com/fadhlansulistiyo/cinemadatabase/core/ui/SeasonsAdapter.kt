package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Seasons
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.core.utils.toEpisodeString
import com.fadhlansulistiyo.cinemadatabase.core.utils.toFormattedDateString
import com.fadhlansulistiyo.cinemadatabase.core.utils.toVoteAverageFormat
import com.fadhlansulistiyo.core.R
import com.fadhlansulistiyo.core.databinding.ItemSeasonsBinding

class SeasonsAdapter : ListAdapter<Seasons, SeasonsAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemSeasonsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val season = getItem(position)
        holder.bind(season)
    }

    class ListViewHolder(private val binding: ItemSeasonsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(season: Seasons) {
            binding.itemName.text = season.name
            binding.itemAirDate.text = season.airDate.toFormattedDateString()
            binding.itemEpisodeCount.text = season.episodeCount.toEpisodeString()
            binding.itemOverview.text = season.overview
            binding.itemVoteAverage.text = season.voteAverage.toVoteAverageFormat(1)
            Glide.with(itemView.context)
                .load(IMAGE_URL + season.posterPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                        .error(R.drawable.ic_error)
                )
                .into(binding.itemPosterPath)

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Seasons>() {
            override fun areItemsTheSame(oldItem: Seasons, newItem: Seasons): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Seasons, newItem: Seasons): Boolean {
                return oldItem == newItem
            }
        }
    }
}