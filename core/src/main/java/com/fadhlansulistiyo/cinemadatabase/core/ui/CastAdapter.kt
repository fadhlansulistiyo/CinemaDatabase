package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Cast
import com.fadhlansulistiyo.cinemadatabase.core.utils.loadImage
import com.fadhlansulistiyo.core.databinding.ItemCastBinding

class CastAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<Cast, CastAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast, onItemClick)
    }

    class ListViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast, onItemClick: (Int) -> Unit) {
            with(binding) {
                itemName.text = cast.name
                itemCharacter.text = cast.character
                itemProfilePeople.loadImage(itemView.context, cast.profilePath)
            }

            itemView.setOnClickListener {
                onItemClick(cast.id)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        }
    }
}