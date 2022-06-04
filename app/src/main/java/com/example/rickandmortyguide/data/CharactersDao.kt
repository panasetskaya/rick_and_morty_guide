package com.example.rickandmortyguide.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT count(*) FROM character")
    suspend fun checkIfIsEmpty(): Int

    @Query("SELECT * FROM character WHERE name LIKE '%' || :search || '%'")
    fun getCharactersBySearch(search: String): PagingSource<Int, Character>

}