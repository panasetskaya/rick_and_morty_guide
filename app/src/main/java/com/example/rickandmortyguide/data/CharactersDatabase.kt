package com.example.rickandmortyguide.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmortyguide.domain.Character

@Database(entities = [Character::class], version = 2, exportSchema = false)
abstract class CharactersDatabase: RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    companion object {

        private const val DB_NAME = "rick_morty_characters.db"
        private var database: CharactersDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): CharactersDatabase {
            synchronized(LOCK) {
                if (database==null) {
                    val instance = Room.databaseBuilder(context, CharactersDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                    database = instance
                    return instance
                } else {
                    return database as CharactersDatabase
                }
            }
        }
    }
}