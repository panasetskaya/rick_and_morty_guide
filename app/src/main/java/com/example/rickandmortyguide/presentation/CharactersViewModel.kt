package com.example.rickandmortyguide.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.rickandmortyguide.data.CharactersRepositoryImpl
import com.example.rickandmortyguide.domain.Character
import com.example.rickandmortyguide.domain.GetCharacterByIdUseCase
import com.example.rickandmortyguide.domain.GetSearchedListUseCase
import com.example.rickandmortyguide.domain.GetWholeListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(application: Application, repo: CharactersRepositoryImpl) : AndroidViewModel(application) {

    private val getCharacterByIdUseCase = GetCharacterByIdUseCase(repo)
    private val getWholeListUseCase = GetWholeListUseCase(repo)
    private val getSearchedListUseCase = GetSearchedListUseCase(repo)

    private val _characterLiveData = MutableLiveData<Character>()
    val characterLiveData: LiveData<Character> = _characterLiveData

    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            _characterLiveData.postValue(getCharacterByIdUseCase.getCharacterById(id))
        }
    }

    fun getWholeList(): Flow<PagingData<Character>> {
        Log.i("MyRes", "CharactersViewModel.getWholeList()")
        return getWholeListUseCase.getWholeList().cachedIn(viewModelScope)
    }

    fun getSearchedList(query: String): Flow<PagingData<Character>> {
        val lowerQuery = query.lowercase()
        val upperQuery = query.replaceFirstChar {
            it.uppercase()
        }
        return getSearchedListUseCase.getSearchedList(query).cachedIn(viewModelScope)
            .map {
            it.filter { character ->
                if (character.name != null) {
                    character.name.contains(lowerQuery) || character.name.contains(upperQuery)
                } else {
                    false
                }
            }
        }
    }
}
