package com.example.rickandmortyguide.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyguide.domain.Character
import com.example.rickandmortyguide.domain.GetSearchedListUseCase
import com.example.rickandmortyguide.domain.GetWholeListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    application: Application,
    private val getWholeListUseCase: GetWholeListUseCase,
    private val getSearchedListUseCase: GetSearchedListUseCase
) :
    AndroidViewModel(application) {


    fun getWholeList(): Flow<PagingData<Character>> {
        Log.i("MyRes", "CharactersViewModel.getWholeList()")
        return getWholeListUseCase.getWholeList().cachedIn(viewModelScope)
    }

    fun getSearchedList(query: String): Flow<PagingData<Character>> {
        return getSearchedListUseCase.getSearchedList(query).cachedIn(viewModelScope)
    }
}
