package com.example.rickandmortyguide.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CharactersViewModel
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private lateinit var recyclerViewCharacters: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewCharacters = findViewById(R.id.recyclerCharacters)
        viewModel = ViewModelProvider(
            this,
            CharactersViewModelFactory(application)
        )[CharactersViewModel::class.java]
        setAdapter()
        lifecycleScope.launch {
            viewModel.getWholeList().distinctUntilChanged().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun setAdapter() {
        pagingAdapter = CharacterPagingAdapter()
        recyclerViewCharacters.adapter = pagingAdapter.withLoadStateAdapters(
            header = CharactersLoadStateAdapter(pagingAdapter),
            footer = CharactersLoadStateAdapter(pagingAdapter))
        pagingAdapter.onCharacterClick = {
            Toast.makeText(this, "There will be some thing here with the character: ${it.name}", Toast.LENGTH_SHORT).show()
        }
    }
}