package com.example.rickandmortyguide.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyguide.data.CharacterDtoDb

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characterdtodb WHERE id==:requiredId")
    suspend fun getCharacterById(requiredId: Int): CharacterDtoDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterDtoDb)

    @Query("DELETE FROM characterdtodb")
    suspend fun deleteAll()

    @Query("SELECT * FROM characterdtodb")
    fun getWholeList(): PagingSource<Int, CharacterDtoDb>

    @Query("SELECT count(*) FROM characterdtodb")
    suspend fun checkIfIsEmpty(): Int

    @Query("SELECT * FROM characterdtodb WHERE name LIKE '%' || :search || '%'")
    fun getCharactersBySearch(search: String): PagingSource<Int, CharacterDtoDb>

    @Query("SELECT * FROM characterdtodb ORDER BY id DESC LIMIT 1")
    suspend fun getLastCharacter(): CharacterDtoDb

}