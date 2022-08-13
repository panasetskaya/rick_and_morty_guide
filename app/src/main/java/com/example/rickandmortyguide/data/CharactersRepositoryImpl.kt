package com.example.rickandmortyguide.data

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
    val mapper: CharacterMapper,
    val db: CharactersDatabase,
    val apiService: ApiPagingService,
    val characterRemoteMediator: CharacterRemoteMediator
) : CharactersRepository {

    override fun getWholeList(): Flow<PagingData<Character>> {
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
        val pager = Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = 20
            ),
            remoteMediator = characterRemoteMediator
        ) {
            db.charactersDao().getWholeList()
        }
        return pager
    }

    private fun loadSearchedCharacters(query: String): Pager<Int, CharacterDtoDb> {

        val pager = Pager(
            PagingConfig(
                enablePlaceholders = true,
                pageSize = 20
            )
        ) {
            CharacterPagingSource(apiService, query)
        }
        return pager
    }
}