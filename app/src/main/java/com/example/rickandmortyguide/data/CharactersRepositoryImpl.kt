package com.example.rickandmortyguide.data

import android.content.Context
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
        return loadAllCharacters().flow
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun loadAllCharacters(): Pager<Int, Character> {
        val pager = Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = 20
            ),
            remoteMediator = CharacterRemoteMediator(db, apiService)
        ) {
            db.charactersDao().getWholeList()
        }
        return pager
    }
}