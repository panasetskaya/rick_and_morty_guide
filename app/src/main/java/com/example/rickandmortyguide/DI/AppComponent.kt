package com.example.rickandmortyguide.DI

import android.app.Application
import android.content.Context
import com.example.rickandmortyguide.data.CharactersRepositoryImpl
import com.example.rickandmortyguide.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CharacterModule::class, RoomModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(
            @BindsInstance
            context: Context,
            @BindsInstance
            application: Application)
        : AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(repo: CharactersRepositoryImpl)

  //  fun getDatabase(): CharactersDatabase

}