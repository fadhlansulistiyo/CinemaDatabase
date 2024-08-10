package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.IMAGE_URL
import com.fadhlansulistiyo.core.R
import com.fadhlansulistiyo.core.databinding.ItemListPersonBinding

class PopularPeopleAdapter(private val onItemClick: (Int) -> Unit) :
    PagingDataAdapter<PopularPeople, PopularPeopleAdapter.PopularPeopleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPeopleViewHolder {
        val binding =
            ItemListPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularPeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularPeopleViewHolder, position: Int) {
        val person = getItem(position)
        person?.let { holder.bind(it, onItemClick) }
    }

    class PopularPeopleViewHolder(private val binding: ItemListPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: PopularPeople, onItemClick: (Int) -> Unit) {
            binding.name.text = person.name
            Glide.with(itemView.context)
                .load(IMAGE_URL + person.profilePath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_person_grey_32)
                        .error(R.drawable.ic_error)
                )
                .into(binding.profilePath)

            itemView.setOnClickListener {
                onItemClick(person.id)
            }

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularPeople>() {
            override fun areItemsTheSame(oldItem: PopularPeople, newItem: PopularPeople): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PopularPeople,
                newItem: PopularPeople
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
