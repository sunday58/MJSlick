package com.gwc.ui.chips.shirt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.gwc.R
import com.gwc.ui.adapters.MaleClothsAdapter
import kotlinx.android.synthetic.main.chip_shirt_fragment.view.*
import kotlinx.android.synthetic.main.chip_shirt_fragment.view.spinKit
import java.util.*

class ChipShirtFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewModel: ChipShirtViewModel
    private lateinit var adapter: MaleClothsAdapter
    private val indicatorColor: Int = R.color.white

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.chip_shirt_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ChipShirtViewModel::class.java)
        root.all_shirt.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_chipsAll)
        }
        root.trouser_shirt.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_chipsTrouser)
        }
        root.spinKit.visibility = View.VISIBLE
        swipeRefresh()
        displayCloths()
        navigateBack(root.shirt_toolbar)
        return root
    }

    private fun displayCloths(){
        root.chips_shirt_recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getMaleShirts {shirts ->
            root.shirt_toolbar.title = "Male Shirts"
            adapter = MaleClothsAdapter(shirts)
            root.chips_shirt_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            root.spinKit.visibility = View.GONE
        }
    }

    private fun swipeRefresh(){
        root.shirtSwipeRefresh.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark)
        root.shirtSwipeRefresh.setColorSchemeResources(indicatorColor)
        root.shirtSwipeRefresh.setOnRefreshListener {
            root.shirtSwipeRefresh.isRefreshing = false
            shuffleItems()
        }
    }

    @Suppress("JavaCollectionsStaticMethodOnImmutableList")
    private fun shuffleItems(){
        viewModel.getMaleShirts {wears->
            Collections.shuffle(wears, Random(System.currentTimeMillis()))
            adapter = MaleClothsAdapter(wears)
            root.chips_shirt_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
    private fun navigateBack(toolbar: MaterialToolbar){
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(root).navigateUp()
        }
    }

}