package com.gwc.ui.chips.all

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
import kotlinx.android.synthetic.main.fragment_chips_all.view.*
import kotlinx.android.synthetic.main.fragment_chips_all.view.spinKit
import java.util.*

class ChipsAllFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewModel: ChipsAllViewModel
    private lateinit var adapter: MaleClothsAdapter
    private val indicatorColor: Int = R.color.white

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_chips_all, container, false)
        viewModel = ViewModelProvider(this).get(ChipsAllViewModel::class.java)

        root.trousers_detail.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_chipsTrouser)
        }
        root.shirts_detail.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_chipsShirt)
        }
        root.spinKit.visibility = View.VISIBLE
        swipeRefresh()
        navigateBack(root.all_toolbar)
        displayCloths()
        return root
    }

    private fun displayCloths(){
        root.chips_all_recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getMaleCloths {wears->
            root.all_toolbar.title = "All Male Wears"
            adapter = MaleClothsAdapter(wears)
            root.chips_all_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            root.spinKit.visibility = View.GONE
        }
    }
    private fun swipeRefresh(){
        root.allSwipeRefresh.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark)
        root.allSwipeRefresh.setColorSchemeResources(indicatorColor)
        root.allSwipeRefresh.setOnRefreshListener {
            root.allSwipeRefresh.isRefreshing = false
            shuffleItems()
        }
    }

    @Suppress("JavaCollectionsStaticMethodOnImmutableList")
    private fun shuffleItems(){
        viewModel.getMaleCloths {wears->
            Collections.shuffle(wears, Random(System.currentTimeMillis()))
            adapter = MaleClothsAdapter(wears)
            root.chips_all_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun navigateBack(toolbar: MaterialToolbar){
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(root).navigateUp()
        }
    }
}