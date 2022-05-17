package com.example.rickandmortyguide.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyguide.domain.Character
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX = 1

class CharactersPagingSource(val context: Context, val db: CharactersDatabase, private val api: ApiPagingService): PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX

        return try {
            var result = listOf<Character>()
            api.getPagingCharactersExample(pageIndex).characters?.let {
                result = it
            }
            GlobalScope.launch {
                insertCharactersIntoDb(db, result)
            }

            // ЗАМЕНИТЬ GlobalScope?????

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

    private suspend fun insertCharactersIntoDb(db: CharactersDatabase, list: List<Character>) {
        for (i in list) {
            db.charactersDao().insertCharacter(i)
        }
    }
}