package com.example.rickandmortyguide.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortyguide.DI.AppComponent
import com.example.rickandmortyguide.DI.DaggerAppComponent
import com.example.rickandmortyguide.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext, application)
    }

    @Inject
    lateinit var viewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.inject(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }
}
