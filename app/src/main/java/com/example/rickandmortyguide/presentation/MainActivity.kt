package com.example.rickandmortyguide.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CharactersViewModel
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var topAppBar: MaterialToolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        topAppBar = findViewById(R.id.topAppBar)
        topAppBar.setOnMenuItemClickListener { menuItem ->
            // Handle search icon press
            Toast.makeText(this, "There will be search here", Toast.LENGTH_SHORT).show()
            true
        }

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
            it.id?.let { id ->
                val fragment = DetailsFragment.newInstance(id)
                launchFragment(fragment)
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.character_container_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}