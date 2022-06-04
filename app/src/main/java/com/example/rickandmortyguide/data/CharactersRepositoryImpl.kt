package com.example.rickandmortyguide.data

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyguide.domain.Character
import com.example.rickandmortyguide.domain.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(val context: Context) : CharactersRepository {

    val apiService = ApiPagingService.getService()
    val db = CharactersDatabase.getInstance(context)

    override suspend fun getCharacterById(id: Int): Character {
        return db.charactersDao().getCharacterById(id)
    }

    override fun getWholeList(): Flow<PagingData<Character>> {
        Log.i("MyRes", "CharactersRepositoryImpl.getWholeList()")
        return loadAllCharacters().flow
    }

    override fun getSearchedList(query:String): Flow<PagingData<Character>> {
        return loadSearchedCharacters(query).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun loadAllCharacters(): Pager<Int, Character> {
        Log.i("MyRes", "loadAllCharacters()")
        val pager = Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = 20
            ),
            remoteMediator = CharacterRemoteMediator(db, apiService)
        ) {
            Log.i("MyRes", "db.charactersDao().getWholeList()")
            db.charactersDao().getWholeList()
        }
        Log.i("MyRes", "pager")
        return pager
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun loadSearchedCharacters(query: String): Pager<Int, Character> {
        Log.i("MyRes", "loadAllCharacters()")
        val pager = Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = 20
            ),
            remoteMediator = SearchCharacterRemoteMediator(db, apiService, query)
        ) {
            db.charactersDao().getCharactersBySearch(query)
        }
        Log.i("MyRes", "search pager")
        return pager
    }
}