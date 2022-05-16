package com.example.rickandmortyguide.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class GetWholeListUseCase(private val repository: CharactersRepository) {

    fun getWholeList(): Flow<PagingData<Character>> {
        return repository.getWholeList()
    }

}