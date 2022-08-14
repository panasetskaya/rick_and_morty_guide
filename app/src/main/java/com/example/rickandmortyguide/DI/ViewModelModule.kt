package com.example.rickandmortyguide.DI

import androidx.lifecycle.ViewModel
import com.example.rickandmortyguide.presentation.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {

    @IntoMap
    @StringKey("CharactersViewModel")
    @Binds
    fun bindCharacterViewModel(impl: CharactersViewModel): ViewModel
}