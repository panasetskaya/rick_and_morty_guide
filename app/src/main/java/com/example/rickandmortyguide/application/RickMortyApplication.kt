package com.example.rickandmortyguide.application

import android.app.Application
import com.example.rickandmortyguide.DI.DaggerAppComponent

class RickMortyApplication: Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext, this)
    }

}