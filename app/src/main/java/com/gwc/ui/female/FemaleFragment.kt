package com.gwc.ui.female

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.gwc.R
import com.gwc.ui.adapters.FemaleClothsAdapter
import com.gwc.ui.factory.GetFemaleClothFactory
import kotlinx.android.synthetic.main.female_fragment.view.*
import kotlinx.android.synthetic.main.female_fragment.view.spinKit
import java.util.*

class FemaleFragment : Fragment() {

    private lateinit var viewModel: FemaleViewModel
    private lateinit var adapter: FemaleClothsAdapter
    private lateinit var root: View
    private val indicatorColor: Int = R.color.white

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.female_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = GetFemaleClothFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FemaleViewModel::class.java)

        //check admin
        if (viewModel.currentUser() != null){
            root.add_female_wear.visibility = View.VISIBLE
            root.add_female_wear.setOnClickListener {
                Navigation.findNavController(root).navigate(R.id.navigation_addFemaleCloth)
            }
        }else {
            root.add_female_wear.visibility = View.GONE
        }

        root.female_search_placeHolder.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_femaleSearch)
        }
        root.spinKit.visibility = View.VISIBLE
        displayCloths()
        swipeRefresh()
        return root
    }

    private fun displayCloths(){
        root.female_wear_recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getFemaleCloths {wears ->
            adapter = FemaleClothsAdapter(wears)
            root.female_wear_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            root.spinKit.visibility = View.GONE
        }
    }

    private fun swipeRefresh(){
        root.femaleClothSwipe.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark)
        root.femaleClothSwipe.setColorSchemeResources(indicatorColor)
        root.femaleClothSwipe.setOnRefreshListener {
            root.femaleClothSwipe.isRefreshing = false
            shuffleItems()
        }
    }

    @Suppress("JavaCollectionsStaticMethodOnImmutableList")
    private fun shuffleItems(){
        viewModel.getFemaleCloths {wears->
            Collections.shuffle(wears, Random(System.currentTimeMillis()))
            adapter = FemaleClothsAdapter(wears)
            root.female_wear_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

}