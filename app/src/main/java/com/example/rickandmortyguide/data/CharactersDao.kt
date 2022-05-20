package com.example.rickandmortyguide.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyguide.domain.Character

@Dao
interface CharactersDao {

    @Query("SELECT * FROM character WHERE id==:requiredId")
    suspend fun getCharacterById(requiredId: Int): Character

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacter(character: Character)

    @Query("SELECT * FROM character")
    suspend fun getWholeList(): List<Character>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :search || '%'")
    suspend fun getCharactersBySearch(search: String): List<Character>

}