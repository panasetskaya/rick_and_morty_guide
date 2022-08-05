package com.example.rickandmortyguide.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getWholeList(): Flow<PagingData<Character>>

    fun getSearchedList(query: String): Flow<PagingData<Character>>

}