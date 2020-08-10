package com.mjslick.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mjslick.R
import kotlinx.android.synthetic.main.female_search_fragment.view.*

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