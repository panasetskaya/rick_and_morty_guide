package com.example.rickandmortyguide.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.R
import com.example.rickandmortyguide.domain.Character
import com.squareup.picasso.Picasso

class CharacterPagingAdapter: PagingDataAdapter<Character, CharacterPagingAdapter.CharacterViewHolder>(COMPARATOR) {

    var onCharacterClick: ((Character) -> Unit)? = null

    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        companion object {
            fun getInstance(parent: ViewGroup): CharacterViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.character_item, parent,false)
                return CharacterViewHolder(view)
            }
        }
        var textViewCharacterName: TextView = itemView.findViewById(R.id.textViewCharacterName)
        var imageViewCharacterImage: ImageView = itemView.findViewById(R.id.imageViewCharacterImage)
        val thisItemView = itemView
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { char ->
            holder.textViewCharacterName.text = char.name
            Picasso.get().load(char.image).into(holder.imageViewCharacterImage)
            holder.thisItemView.setOnClickListener { onCharacterClick?.invoke(char) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.getInstance(parent)
    }
}