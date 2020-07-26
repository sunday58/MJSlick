package com.mjslick.ui.male

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mjslick.R

class MaleFragment : Fragment() {

    companion object {
        fun newInstance() = MaleFragment()
    }

    private lateinit var viewModel: MaleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.male_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MaleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}