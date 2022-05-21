package com.example.rickandmortyguide.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


class CharacterListFragment : Fragment() {

    private lateinit var viewModel: CharactersViewModel
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topAppBar = view.findViewById(R.id.topAppBarMain)
        topAppBar.setOnMenuItemClickListener { menuItem ->
            // Handle search icon press
            Toast.makeText(activity, "There will be search here", Toast.LENGTH_SHORT).show()
            true
        }

        recyclerViewCharacters = view.findViewById(R.id.recyclerCharacters)
        viewModel = ViewModelProvider(
            requireActivity(),
            CharactersViewModelFactory(requireActivity().application)
        )[CharactersViewModel::class.java]
        setAdapter()
        lifecycleScope.launch {
            viewModel.getWholeList().distinctUntilChanged().collectLatest {
                pagingAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setAdapter() {
        pagingAdapter = CharacterPagingAdapter()
        recyclerViewCharacters.adapter = pagingAdapter.withLoadStateAdapters(
            header = CharactersLoadStateAdapter(pagingAdapter),
            footer = CharactersLoadStateAdapter(pagingAdapter))

        // Не работает Restoration Policy!
        pagingAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        pagingAdapter.onCharacterClick = {
            it.id?.let { id ->
                val action = CharacterListFragmentDirections.actionCharacterListFragmentToDetailsFragment(id)
                recyclerViewCharacters.findNavController().navigate(action)
            // TODO("ПРОБЛЕМЫ: CharacterListFragment при возврате на него загружается заново (без кэша в датабазе!)
            //  и теряет позицию, несмотря на StateRestorationPolicy.
            //  Посмотреть в логах бэкстек и жц этого фрагмента")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharacterListFragment()
    }
}
