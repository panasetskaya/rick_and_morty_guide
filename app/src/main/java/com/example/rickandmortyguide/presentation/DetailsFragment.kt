package com.example.rickandmortyguide.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.rickandmortyguide.R
import com.example.rickandmortyguide.databinding.FragmentDetailsBinding
import com.example.rickandmortyguide.domain.Character


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailsBinding == null")

    private lateinit var characterParam: Character
    val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterParam = args.idParam
        setValues(view)
    }


    private fun setAppBar() {
        binding.topAppBarDetail.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            true
        }
    }

    private fun setValues(view: View) {
        setAppBar()
        characterParam.let { character ->
            with(binding) {
                textViewDetailName.text = character.name
                textViewDetailSpecies.text = character.species
                textViewDetailCreated.text = character.created
                textViewDetailGender.text = character.gender
                textViewDetailStatus.text = character.status
                topAppBarDetail.title = character.name
                Glide.with(view.context).load(character.image)
                    .placeholder(R.drawable.img)
                    .into(imageViewCharacterImageDetail)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}