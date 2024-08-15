package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import com.fadhlansulistiyo.cinemadatabase.core.utils.loadImage
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
            with(binding) {
                name.text = person.name
                profilePath.loadImage(itemView.context, person.profilePath)
            }

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
