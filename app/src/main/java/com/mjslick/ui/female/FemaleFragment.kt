package com.mjslick.ui.female

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.mjslick.R
import com.mjslick.ui.adapters.FemaleClothsAdapter
import com.mjslick.ui.factory.GetFemaleClothFactory
import kotlinx.android.synthetic.main.female_fragment.view.*

class FemaleFragment : Fragment() {

    private lateinit var viewModel: FemaleViewModel
    private lateinit var adapter: FemaleClothsAdapter
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.female_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = GetFemaleClothFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FemaleViewModel::class.java)

        root.add_female_wear.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_addFemaleCloth)
        }
        root.female_search_placeHolder.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_femaleSearch)
        }
        displayCloths()
        return root
    }

    private fun displayCloths(){
        root.female_wear_recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getFemaleCloths {wears ->
            adapter = FemaleClothsAdapter(wears)
            root.female_wear_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

}