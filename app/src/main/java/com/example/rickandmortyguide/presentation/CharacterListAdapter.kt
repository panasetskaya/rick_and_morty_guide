package com.example.rickandmortyguide.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyguide.R
import com.example.rickandmortyguide.domain.Character

class CharacterListAdapter: ListAdapter<Character, CharacterListAdapter.CharacterViewHolder>(CharacterDiffCallBack()) {

    var onShopItemClickListener: ((Character) -> Unit)? = null

    class CharacterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.imageViewCharacterImage)
        val name = view.findViewById<TextView>(R.id.textViewCharacterName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.character_item,
            parent,
            false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        Glide.with(holder.view.context).load(item.image).circleCrop()
            .into(holder.image)
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(item)
            true
        }
    }

}