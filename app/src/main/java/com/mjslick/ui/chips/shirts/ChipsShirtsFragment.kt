package com.mjslick.ui.chips.shirts

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mjslick.R

class ChipsShirtsFragment : Fragment() {

    companion object {
        fun newInstance() = ChipsShirtsFragment()
    }

    private lateinit var viewModel: ChipsShirtsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chips_shirts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChipsShirtsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}