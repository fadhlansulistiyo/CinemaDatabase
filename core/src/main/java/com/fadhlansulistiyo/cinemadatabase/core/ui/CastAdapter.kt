package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Cast
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.core.R
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
            binding.itemName.text = cast.name
            binding.itemCharacter.text = cast.character
            Glide.with(itemView.context)
                .load(IMAGE_URL + cast.profilePath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_person_grey_32)
                        .error(R.drawable.ic_error)
                )
                .into(binding.itemProfilePeople)

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