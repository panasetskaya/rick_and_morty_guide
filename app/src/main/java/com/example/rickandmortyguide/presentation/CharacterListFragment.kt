package com.example.rickandmortyguide.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.capitalize
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.R
import com.example.rickandmortyguide.domain.Character
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class CharacterListFragment : Fragment() {

    private lateinit var viewModel: CharactersViewModel
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            CharactersViewModelFactory(requireActivity().application)
        )[CharactersViewModel::class.java]
        setViews(view)
        setAdapter()
        searching(searchView)
        launchViewModel("")
    }

    private fun setViews(view: View) {
        recyclerViewCharacters = view.findViewById(R.id.recyclerCharacters)
        searchView = view.findViewById(R.id.searchViewMain)
    }

    private fun setAdapter() {
        pagingAdapter = CharacterPagingAdapter()
        recyclerViewCharacters.adapter = pagingAdapter.withLoadStateAdapters(
            header = CharactersLoadStateAdapter(pagingAdapter),
            footer = CharactersLoadStateAdapter(pagingAdapter)
        )
        pagingAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        pagingAdapter.onCharacterClick = { character ->
            val action =
                CharacterListFragmentDirections.actionCharacterListFragmentToDetailsFragment(character.id)
            recyclerViewCharacters.findNavController().navigate(action)
        }
    }

    private fun searching(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newQuery: String?): Boolean {
                if (newQuery != null) {
                    launchViewModel(newQuery)
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                launchViewModel(newText)
                return false
            }

        })
    }

    private fun launchViewModel(query: String) {
        val lowerQuery = query.lowercase()
        val upperQuery = query.replaceFirstChar {
            it.uppercase()
        }
        lifecycleScope.launch {
            viewModel.getWholeList()
                .map { pagingData ->
                    pagingData.filter { character ->
                        if (character.name!=null) {
                            character.name.contains(lowerQuery) || character.name.contains(upperQuery)
                        } else {
                            false
                        }
                    }
                }
                .distinctUntilChanged().collectLatest {pagingData ->
                    pagingAdapter.submitData(lifecycle, pagingData)
                }
        }
    }
}
