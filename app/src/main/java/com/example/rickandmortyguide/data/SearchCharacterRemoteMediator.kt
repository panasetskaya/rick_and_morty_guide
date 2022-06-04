package com.example.rickandmortyguide.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmortyguide.domain.Character
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class SearchCharacterRemoteMediator(
    private val database: CharactersDatabase,
    private val networkService: ApiPagingService,
    private val query: String
) : RemoteMediator<Int, Character>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                    if (prevKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    if (nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    nextKey
                }
            }
            val apiResponse = networkService.getSearchedCharactersExample(query, loadKey)

            val characters = apiResponse.characters
            val endOfPaginationReached = characters.isEmpty()
            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.searchRemoteKeysDao().clearSearchRemoteKeys()
          //          database.charactersDao().deleteAll()
                }
                val prevKey = if (loadKey == STARTING_PAGE_INDEX) null else loadKey - 1
                val nextKey = if (endOfPaginationReached) null else loadKey + 1
                val keys = characters.map {
                    Log.i("MyRes", "keys = characters.map $it")
                    SearchRemoteKeys(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.searchRemoteKeysDao().insertAll(keys)
                for (i in characters) {
                    database.charactersDao().insertCharacter(i)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            Log.i("MyRes", "IOException")
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.i("MyRes", "HttpException")
            MediatorResult.Error(e)
        }


    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): SearchRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                // Get the remote keys of the last item retrieved
                database.searchRemoteKeysDao().searchRemoteKeysRepoId(character.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): SearchRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                // Get the remote keys of the first items retrieved
                database.searchRemoteKeysDao().searchRemoteKeysRepoId(character.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): SearchRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                database.searchRemoteKeysDao().searchRemoteKeysRepoId(characterId)
            }
        }
    }
}