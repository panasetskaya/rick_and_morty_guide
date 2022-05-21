package com.example.rickandmortyguide.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

const val TAG = "MyRes"

class CharacterListFragment : Fragment() {

    private lateinit var viewModel: CharactersViewModel
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "CharacterListFragment : onCreateView")
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "CharacterListFragment : onViewCreated")
        setViews(view)
        setAdapter()
        launchViewModel()
    }

    private fun launchViewModel() {
        viewModel = ViewModelProvider(
            this,
            CharactersViewModelFactory(requireActivity().application)
        )[CharactersViewModel::class.java]
        lifecycleScope.launch {
            viewModel.getWholeList().distinctUntilChanged().collectLatest {
                pagingAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setViews(view: View) {
        searchView = view.findViewById(R.id.searchViewMain)
        searching(searchView)
        recyclerViewCharacters = view.findViewById(R.id.recyclerCharacters)
    }

    private fun setAdapter() {
        pagingAdapter = CharacterPagingAdapter()
        recyclerViewCharacters.adapter = pagingAdapter.withLoadStateAdapters(
            header = CharactersLoadStateAdapter(pagingAdapter),
            footer = CharactersLoadStateAdapter(pagingAdapter)
        )

        // Не работает Restoration Policy!
        pagingAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        pagingAdapter.onCharacterClick = {
            val action =
                CharacterListFragmentDirections.actionCharacterListFragmentToDetailsFragment(it.id)
            recyclerViewCharacters.findNavController().navigate(action)
        }
    }

    private fun searching(search: SearchView) {
        Log.i(TAG, "CharacterListFragment : searching")
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newQuery: String?): Boolean {
                if (newQuery!=null) {
                    lifecycleScope.launch {
                        viewModel.getCharacterBySearch(newQuery).distinctUntilChanged().collectLatest {
                            pagingAdapter.submitData(lifecycle, it)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                lifecycleScope.launch {
                    viewModel.getCharacterBySearch(newText).distinctUntilChanged().collectLatest {
                        pagingAdapter.submitData(lifecycle, it)
                    }
                }
                return true
            }
        })
    }
}
