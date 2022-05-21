package com.example.rickandmortyguide.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.R
import com.example.rickandmortyguide.domain.Character
import com.google.android.material.appbar.MaterialToolbar

class SearchFragment : Fragment() {

    private var query: String? = null
    private lateinit var viewModel: CharactersViewModel
    private lateinit var listAdapter: CharacterListAdapter
    private lateinit var recyclerViewFoundCharacters: RecyclerView
    private lateinit var topAppBar: MaterialToolbar
    val args: SearchFragmentArgs by navArgs()
    private var onShopItemClickListener: ((Character) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query = args.query
        setViews(view)
        setAdapter()
        setViewModel()
    }

    private fun setViews(view: View) {
        recyclerViewFoundCharacters = view.findViewById(R.id.recyclerCharactersSearch)
        topAppBar = view.findViewById(R.id.topAppBarSearch)
        topAppBar.setOnMenuItemClickListener { menuItem ->
            // Handle search icon press
            Toast.makeText(activity, "There will be search here", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun setAdapter() {
        listAdapter = CharacterListAdapter()
        recyclerViewFoundCharacters.adapter = listAdapter
        onShopItemClickListener = {
            it.id?.let { id ->
                val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(id)
                recyclerViewFoundCharacters.findNavController().navigate(action)
            }
        }
        listAdapter.onShopItemClickListener = onShopItemClickListener
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            CharactersViewModelFactory(requireActivity().application)
        )[CharactersViewModel::class.java]
        viewModel.getCharacterBySearch(query)
        viewModel.characterListLiveData.observe(viewLifecycleOwner) {
            if (it!=null) {
                listAdapter.submitList(it)
            }
        }
    }


}