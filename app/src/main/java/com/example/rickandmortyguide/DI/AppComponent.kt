package com.example.rickandmortyguide.DI

import android.app.Application
import android.content.Context
import com.example.rickandmortyguide.presentation.CharacterListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [CharacterModule::class, RoomModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context,
            @BindsInstance
            application: Application)
        : AppComponent
    }

    fun inject(characterListFragment: CharacterListFragment)

}