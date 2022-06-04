package com.example.rickandmortyguide.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.filter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.R
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
        viewModel = (activity as MainActivity).viewModel
        setViews(view)
        setAdapter()
        if (searchView.isIconified) {
            launchWholeList()
            searching(searchView)
        } else {
            searching(searchView)}
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
                CharacterListFragmentDirections.actionCharacterListFragmentToDetailsFragment(
                    character
                )
            recyclerViewCharacters.findNavController().navigate(action)
        }
    }

    private fun searching(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newQuery: String?): Boolean {
                if (newQuery != null) {
                    launchSearch(newQuery)
                    pagingAdapter.refresh()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                launchSearch(newText)
                pagingAdapter.refresh()
                return true
            }
        })
        search.setOnCloseListener {
            launchWholeList()
            pagingAdapter.refresh()
            false
        }
    }

    private fun launchWholeList() {
        lifecycleScope.launch {
            viewModel.getWholeList().distinctUntilChanged().collectLatest { pagingData ->
                pagingAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun launchSearch(query: String) {
        lifecycleScope.launch {
            viewModel.getSearchedList(query).distinctUntilChanged().collectLatest { pagData ->
                pagingAdapter.submitData(lifecycle, pagData)

            }
        }
    }
}
