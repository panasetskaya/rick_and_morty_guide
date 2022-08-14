package com.example.rickandmortyguide.DI

import com.example.rickandmortyguide.data.CharactersRepositoryImpl
import com.example.rickandmortyguide.domain.CharactersRepository
import dagger.Binds
import dagger.Module

@Module
abstract class CharacterModule {

    @ApplicationScope
    @Binds
    abstract fun provideRepo(impl: CharactersRepositoryImpl): CharactersRepository
}