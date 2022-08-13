package com.example.rickandmortyguide.data

import android.content.Context
import android.util.Log
import androidx.paging.*
import com.example.rickandmortyguide.data.db.CharactersDatabase
import com.example.rickandmortyguide.data.network.ApiPagingService
import com.example.rickandmortyguide.data.network.CharacterPagingSource
import com.example.rickandmortyguide.data.network.CharacterRemoteMediator
import com.example.rickandmortyguide.domain.Character
import com.example.rickandmortyguide.domain.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    val context: Context,
    val mapper: CharacterMapper,
    val db: CharactersDatabase
) : CharactersRepository {

    val apiService = ApiPagingService.getService()

    override fun getWholeList(): Flow<PagingData<Character>> {
        Log.i("MyRes", "CharactersRepositoryImpl.getWholeList()")
        return loadAllCharacters().flow
            .map {
                it.map { characterDtoDb ->
                    mapper.mapDbModeltoDomainEntity(characterDtoDb)
                }
            }
    }

    override fun getSearchedList(query: String): Flow<PagingData<Character>> {
        return loadSearchedCharacters(query).flow
            .map {
                it.map { characterDtoDb ->
                    mapper.mapDbModeltoDomainEntity(characterDtoDb)
                }
            }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun loadAllCharacters(): Pager<Int, CharacterDtoDb> {
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

    private fun loadSearchedCharacters(query: String): Pager<Int, CharacterDtoDb> {
        Log.i("MyRes", "loadAllCharacters()")
        val pager = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(
                enablePlaceholders = true,
                pageSize = 20
            )
        ) {
            CharacterPagingSource(apiService, query)
        }
        Log.i("MyRes", "search pager")
        return pager
    }
}