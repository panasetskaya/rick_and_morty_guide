package com.example.rickandmortyguide.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmortyguide.domain.Character

class CharacterDiffCallBack: DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}