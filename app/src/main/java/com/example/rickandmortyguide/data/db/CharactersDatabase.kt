package com.example.rickandmortyguide.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortyguide.data.CharacterDtoDb


@Database(entities = [CharacterDtoDb::class, RemoteKeys::class], version = 7, exportSchema = false)
abstract class CharactersDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}