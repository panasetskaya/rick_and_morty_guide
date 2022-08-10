package com.example.rickandmortyguide.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWholeListUseCase @Inject constructor
    (private val repository: CharactersRepository) {

    fun getWholeList(): Flow<PagingData<Character>> {
        return repository.getWholeList()
    }

}