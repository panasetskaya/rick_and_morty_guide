package com.example.rickandmortyguide.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rickandmortyguide.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, CharactersViewModelFactory(application))[CharactersViewModel::class.java]

//        lifecycleScope.launch {
//            viewModel.getWholeList().distinctUntilChanged().collectLatest {
//                pagingAdapter.submitData(it)
//            }
//        }
    }
}