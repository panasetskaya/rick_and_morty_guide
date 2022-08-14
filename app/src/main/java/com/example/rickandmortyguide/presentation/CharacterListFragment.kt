package com.example.rickandmortyguide.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.DI.AppComponent
import com.example.rickandmortyguide.application.RickMortyApplication
import com.example.rickandmortyguide.databinding.FragmentCharacterListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterListFragment : Fragment() {

    val appComponent: AppComponent by lazy {
        (requireActivity().application as RickMortyApplication).appComponent
    }

    private var _binding: FragmentCharacterListBinding? = null
    private val binding: FragmentCharacterListBinding
        get() = _binding ?: throw RuntimeException("FragmentCharacterListBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[CharactersViewModel::class.java]
    }

    private lateinit var pagingAdapter: CharacterPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)
        setAdapter()
        searching(binding.searchViewMain)
        launchWholeList()
    }

    private fun setAdapter() {
        pagingAdapter = CharacterPagingAdapter()
        binding.recyclerCharacters.adapter = pagingAdapter.withLoadStateAdapters(
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
            binding.recyclerCharacters.findNavController().navigate(action)
        }
    }

    private fun searching(search: SearchView) {

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newQuery: String?): Boolean {
                if (newQuery != null) {
                    launchSearch(newQuery)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                launchSearch(newText)
                return false
            }
        })
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
