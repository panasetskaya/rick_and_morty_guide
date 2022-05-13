package com.example.rickandmortyguide.domain

interface CharactersRepository {

    fun getCharacterById(id: Int): Character

    fun getWholeList(): List<Character>

    fun getCharactersBySearch(query: String?): List<Character>?

}