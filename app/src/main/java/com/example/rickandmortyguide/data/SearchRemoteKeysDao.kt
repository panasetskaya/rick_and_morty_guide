package com.example.rickandmortyguide.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(searchRemoteKey: List<SearchRemoteKeys>)

    @Query("SELECT * FROM search_remote_keys WHERE characterId = :characterId")
    suspend fun searchRemoteKeysRepoId(characterId: Int): SearchRemoteKeys?

    @Query("DELETE FROM search_remote_keys")
    suspend fun clearSearchRemoteKeys()
}