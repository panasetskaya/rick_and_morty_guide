package com.example.rickandmortyguide.domain

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class GetWholeListUseCase (private val repository: CharactersRepository) {

    fun getWholeList(): Flow<PagingData<Character>> {
        Log.i("MyRes", "GetWholeListUseCase.getWholeList()")
        return repository.getWholeList()
    }

}