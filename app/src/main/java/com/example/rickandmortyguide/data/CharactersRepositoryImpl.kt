package com.example.rickandmortyguide.data

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyguide.domain.Character
import com.example.rickandmortyguide.domain.CharactersRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class CharactersRepositoryImpl(val context: Context): CharactersRepository {

    val apiService = ApiPagingService.getService()
    val db = CharactersDatabase.getInstance(context)

    override suspend fun getCharacterById(id: Int): Character {
        return db.charactersDao().getCharacterById(id)
    }

    override fun getWholeList(): Flow<PagingData<Character>> {
        return loadCharacters()
    }

    override suspend fun getCharactersBySearch(query: String): List<Character> {
        return  db.charactersDao().getCharactersBySearch(query)
    }

    private fun loadCharacters() = Pager(
        pagingSourceFactory = { CharactersPagingSource(context, db, apiService) },
        config = PagingConfig(
            enablePlaceholders = true,
            pageSize = 20
        )
    ).flow
}