package com.example.rickandmortyguide.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchedListUseCase @Inject constructor
    (private val repository: CharactersRepository) {

    fun getSearchedList(query: String): Flow<PagingData<Character>> {
        return repository.getSearchedList(query)
    }
}