package com.example.rickandmortyguide.DI

import android.app.Application
import android.content.Context
import com.example.rickandmortyguide.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

// Definition of a Dagger component
@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context, @BindsInstance application: Application): AppComponent
    }

    fun inject(activity: MainActivity)

}