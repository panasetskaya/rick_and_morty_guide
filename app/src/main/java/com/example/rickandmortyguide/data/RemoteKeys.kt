package com.example.rickandmortyguide.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val characterId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
