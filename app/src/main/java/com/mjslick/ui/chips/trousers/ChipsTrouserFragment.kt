package com.mjslick.ui.chips.trousers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mjslick.R

class ChipsTrouserFragment : Fragment() {

    companion object {
        fun newInstance() = ChipsTrouserFragment()
    }

    private lateinit var viewModel: ChipsTrouserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chips_trouser_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChipsTrouserViewModel::class.java)
        // TODO: Use the ViewModel
    }

}