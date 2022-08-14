package com.example.rickandmortyguide.DI

import android.content.Context
import androidx.room.Room
import com.example.rickandmortyguide.data.db.CharactersDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @ApplicationScope
    @Provides
    fun provideDatabase(context: Context): CharactersDatabase {
        return Room.databaseBuilder(context, CharactersDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        private const val DB_NAME = "rick_morty_characters.db"
    }

}