package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.utils.loadImage
import com.fadhlansulistiyo.core.databinding.ItemPeopleBinding

class PeopleAdapter(private val onItemClick: (Int) -> Unit) : ListAdapter<People, PeopleAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val people = getItem(position)
        holder.bind(people, onItemClick)
    }

    class ListViewHolder(private val binding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(people: People, onItemClick: (Int) -> Unit) {
            with(binding) {
                itemName.text = people.name
                itemProfilePeople.loadImage(itemView.context, people.profilePath)
            }

            itemView.setOnClickListener {
                onItemClick(people.id)
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<People>() {
            override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
                return oldItem == newItem
            }
        }
    }
}