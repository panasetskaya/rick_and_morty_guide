package com.example.rickandmortyguide.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyguide.data.CharactersRepositoryImpl
import com.example.rickandmortyguide.domain.Character
import com.example.rickandmortyguide.domain.GetCharacterByIdUseCase
import com.example.rickandmortyguide.domain.GetWholeListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


// @Inject tells Dagger how to provide instances of this type
// Dagger also knows that Application is a dependency
class CharactersViewModel (application: Application) : AndroidViewModel(application) {

    private val repo: CharactersRepositoryImpl =
        CharactersRepositoryImpl(getApplication<Application>().applicationContext)

    private val getCharacterByIdUseCase = GetCharacterByIdUseCase(repo)
    private val getWholeListUseCase = GetWholeListUseCase(repo)

    private val _characterLiveData = MutableLiveData<Character>()
    val characterLiveData: LiveData<Character> = _characterLiveData

    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            _characterLiveData.postValue(getCharacterByIdUseCase.getCharacterById(id))
        }
    }

    fun getWholeList(): Flow<PagingData<Character>> {
        return getWholeListUseCase.getWholeList().cachedIn(viewModelScope)
    }
}
