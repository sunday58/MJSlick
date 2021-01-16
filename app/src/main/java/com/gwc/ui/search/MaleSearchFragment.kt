package com.gwc.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gwc.R
import com.gwc.ui.adapters.MaleClothsAdapter
import com.gwc.utility.Network
import kotlinx.android.synthetic.main.male_search_fragment.view.*

class MaleSearchFragment : Fragment() {

    private lateinit var viewModel: MaleSearchViewModel
    private lateinit var root: View
    private lateinit var adapter: MaleClothsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.male_search_fragment, container, false)
        viewModel = ViewModelProvider(this).get(MaleSearchViewModel::class.java)

        getSearchCloths()
        return root
    }

    private fun getSearchCloths(){
        root.male_search_recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        root.maleSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (Network.isOnline(requireContext())){
                    if (newText!!.isNotEmpty()){
                        viewModel.getMaleSearchCloths(newText){wears->
                            adapter = MaleClothsAdapter(wears)
                            root.male_search_recyclerView.adapter = adapter
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