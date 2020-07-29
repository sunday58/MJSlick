package com.mjslick.ui.female

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mjslick.R
import kotlinx.android.synthetic.main.female_fragment.view.*

class FemaleFragment : Fragment() {

    companion object {
        fun newInstance() = FemaleFragment()
    }

    private lateinit var viewModel: FemaleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.female_fragment, container, false)

        root.add_female_wear.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_addFemaleCloth)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FemaleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}