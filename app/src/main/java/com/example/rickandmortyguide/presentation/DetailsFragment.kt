package com.example.rickandmortyguide.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.rickandmortyguide.R
import com.google.android.material.appbar.MaterialToolbar

class DetailsFragment : Fragment() {
    private var idParam: Int? = null
    private lateinit var tvName: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvSpecies: TextView
    private lateinit var tvCreated: TextView
    private lateinit var ivImage: ImageView
    private lateinit var viewModel: CharactersViewModel
    private lateinit var topAppBarDetail: MaterialToolbar
    val args: DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            CharactersViewModelFactory(requireActivity().application)
        )[CharactersViewModel::class.java]
        idParam = args.idParam
        findViews(view)
        setValues(view)
    }

    private fun findViews(view: View) {
        tvName = view.findViewById(R.id.textViewDetailName)
        tvGender = view.findViewById(R.id.textViewDetailGender)
        tvStatus = view.findViewById(R.id.textViewDetailStatus)
        tvSpecies = view.findViewById(R.id.textViewDetailSpecies)
        tvCreated = view.findViewById(R.id.textViewDetailCreated)
        ivImage = view.findViewById(R.id.imageViewCharacterImageDetail)
        topAppBarDetail = view.findViewById(R.id.topAppBarDetail)
    }

    private fun setAppBar() {
        topAppBarDetail.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            true
            }

//        topAppBarDetail.setOnMenuItemClickListener { menuItem ->
//            // Handle search icon press
//            requireActivity().onBackPressed()
//            true
//        }
    }

    private fun setValues(view: View) {
        setAppBar()
        idParam?.let {
            viewModel.getCharacterById(it)
            viewModel.characterLiveData.observe(viewLifecycleOwner){
                tvName.text = it.name
                tvSpecies.text = it.species
                tvCreated.text = it.cutCreated
                tvGender.text = it.gender
                tvStatus.text = it.status
                Glide.with(view.context).load(it.image)
                    .into(ivImage)
                topAppBarDetail.title = it.name
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()
    }
}