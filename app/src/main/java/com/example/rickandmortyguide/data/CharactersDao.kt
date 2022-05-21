package com.example.rickandmortyguide.data

import androidx.paging.PagingSource
import androidx.room.*
import com.example.rickandmortyguide.domain.Character

@Dao
interface CharactersDao {

    @Query("SELECT * FROM character WHERE id==:requiredId")
    suspend fun getCharacterById(requiredId: Int): Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Query("DELETE FROM character")
    suspend fun deleteAll()

    @Query("SELECT * FROM character")
    fun getWholeList(): PagingSource<Int, Character>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :search || '%'")
    fun getCharactersBySearch(search: String): PagingSource<Int, Character>

}