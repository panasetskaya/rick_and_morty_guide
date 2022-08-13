package com.example.rickandmortyguide.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyguide.R
import com.example.rickandmortyguide.databinding.CharacterItemBinding
import com.example.rickandmortyguide.domain.Character

class CharacterPagingAdapter :
    PagingDataAdapter<Character, CharacterPagingAdapter.CharacterViewHolder>(COMPARATOR) {

    var onCharacterClick: ((Character) -> Unit)? = null

    inner class CharacterViewHolder(val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { char ->
            with(holder.binding) {
                textViewCharacterName.text = char.name
                Glide.with(root.context).load(char.image).circleCrop()
                    .placeholder(R.drawable.img)
                    .into(imageViewCharacterImage)
                root.setOnClickListener { onCharacterClick?.invoke(char) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }
}