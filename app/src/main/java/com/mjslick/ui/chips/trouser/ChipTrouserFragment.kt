package com.mjslick.ui.chips.trouser

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.mjslick.R
import com.mjslick.ui.adapters.MaleClothsAdapter
import kotlinx.android.synthetic.main.chip_trouser_fragment.view.*
import kotlinx.android.synthetic.main.fragment_chips_all.view.*

class ChipTrouserFragment : Fragment() {

    private lateinit var adapter: MaleClothsAdapter
    private lateinit var viewModel: ChipTrouserViewModel
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.chip_trouser_fragment, container, false)

        viewModel = ViewModelProvider(this).get(ChipTrouserViewModel::class.java)
        root.allTrouserDetail.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_chipsAll)
        }
        root.shirtTrouserDetail.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_chipsShirt)
        }

        displayCloths()
        navigateBack(root.trouser_toolbar)
        return root
    }

    private fun displayCloths(){
        root.chips_trouser_recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getMaleTrousers {wears->
            root.trouser_toolbar.title = "Male Trousers"
            adapter = MaleClothsAdapter(wears)
            root.chips_trouser_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun navigateBack(toolbar: MaterialToolbar){
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(root).navigateUp()
        }
    }
}