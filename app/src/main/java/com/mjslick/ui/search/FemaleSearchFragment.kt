package com.mjslick.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mjslick.R
import com.mjslick.ui.adapters.FemaleClothsAdapter
import com.mjslick.utility.Network
import kotlinx.android.synthetic.main.female_search_fragment.view.*


class FemaleSearchFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewModel: FemaleSearchViewModel
    private lateinit var adapter: FemaleClothsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.female_search_fragment, container, false)
        viewModel = ViewModelProvider(this).get(FemaleSearchViewModel::class.java)

        getSearchCloths()
        return root
    }

    private fun getSearchCloths(){
        root.search_wear_recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        root.femaleSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (Network.isOnline(requireContext())) {
                    if (newText!!.isNotEmpty()) {
                        viewModel.getFemaleSearchCloths(newText) { wears ->
                            adapter = FemaleClothsAdapter(wears)
                            root.search_wear_recyclerView.adapter = adapter
                            adapter.notifyDataSetChanged()
                        }
                    }
                }else{
                    Toast.makeText(requireContext(), "check Network", Toast.LENGTH_SHORT).show()
                }
                return true
            }

        })
    }

}