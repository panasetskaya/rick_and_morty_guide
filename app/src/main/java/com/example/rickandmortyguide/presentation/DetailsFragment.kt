package com.example.rickandmortyguide.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rickandmortyguide.R
import com.google.android.material.appbar.MaterialToolbar

private const val ID_PARAM = "param"

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idParam = it.getInt(ID_PARAM)
        }
    }

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

    private fun setValues(view: View) {
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
        fun newInstance(idParam: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_PARAM, idParam)
                }
            }
    }
}