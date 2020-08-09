package com.mjslick.ui.chips.shirt

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mjslick.R

class ChipShirtFragment : Fragment() {

    companion object {
        fun newInstance() = ChipShirtFragment()
    }

    private lateinit var viewModel: ChipShirtViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chip_shirt_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChipShirtViewModel::class.java)
        // TODO: Use the ViewModel
    }

}