package com.example.rickandmortyguide.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CharactersViewModelFactory(application: Application):
    ViewModelProvider.AndroidViewModelFactory(application) {

    val context = application

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersViewModel(context as Application) as T
    }


}