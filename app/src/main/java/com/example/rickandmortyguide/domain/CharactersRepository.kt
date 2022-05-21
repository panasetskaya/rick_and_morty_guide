package com.example.rickandmortyguide.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getCharacterById(id: Int): Character

    fun getWholeList(): Flow<PagingData<Character>>

    fun getCharactersBySearch(query: String): Flow<PagingData<Character>>

}