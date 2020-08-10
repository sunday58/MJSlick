package com.mjslick.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mjslick.R

class FemaleSearchFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewModel: FemaleSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.female_search_fragment, container, false)
        viewModel = ViewModelProvider(this).get(FemaleSearchViewModel::class.java)


        return root
    }

}