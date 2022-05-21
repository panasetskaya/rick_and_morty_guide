package com.example.rickandmortyguide.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class GetCharactersBySearchUseCase(private val repository: CharactersRepository) {

    fun getCharactersBySearch(query: String): Flow<PagingData<Character>> {
        return repository.getCharactersBySearch(query)
    }

}