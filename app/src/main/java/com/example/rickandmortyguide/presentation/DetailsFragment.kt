package com.example.rickandmortyguide.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmortyguide.R

private const val ID_PARAM = "param"

class DetailsFragment : Fragment() {
    private var idParam: Int? = null

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