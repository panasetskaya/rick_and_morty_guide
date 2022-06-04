package com.example.rickandmortyguide.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyguide.domain.Character
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX = 0

class CharacterPagingSource(
    private val networkService: ApiPagingService,
    private val query: String
): PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {


        return try {
            val pageIndex = params.key ?: STARTING_PAGE_INDEX
            val apiResponse = networkService.getSearchedCharactersExample(pageIndex,query)
            var result: List<Character>

            apiResponse.characters.let {
                result = it
                Log.i("MyRes", "characters loaded")
            }
            val nextKey =
                if (result.isEmpty()) {
                    null
                } else { pageIndex+1 }

            LoadResult.Page(
                data = result,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex-1,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            Log.i("MyRes", "IOException here")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.i("MyRes", "HttpException here")
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }
    }
}
