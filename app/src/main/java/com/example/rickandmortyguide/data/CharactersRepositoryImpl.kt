package com.example.rickandmortyguide.data

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickandmortyguide.domain.Character
import com.example.rickandmortyguide.domain.CharactersRepository

class CharactersRepositoryImpl(val context: Context): CharactersRepository {

    val apiService = ApiPagingService.getService()
    val db = CharactersDatabase.getInstance(context)

    override fun getCharacterById(id: Int): Character {
        TODO("Not yet implemented")
    }

    override fun getWholeList(): List<Character> {
        TODO("Not yet implemented")
    }

    override fun getCharactersBySearch(query: String?): List<Character>? {
        TODO("Not yet implemented")
    }

    private fun loadCharacters() = Pager(
        pagingSourceFactory = { CharactersPagingSource(context, db, apiService) },
        config = PagingConfig(
            pageSize = 20
        )
    ).flow
}