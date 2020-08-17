package com.mjslick.ui.male

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.mjslick.R
import com.mjslick.ui.adapters.MaleClothsAdapter
import com.mjslick.ui.factory.GetMaleClothFactory
import kotlinx.android.synthetic.main.male_fragment.view.*
import java.util.*
import kotlin.collections.ArrayList

class MaleFragment : Fragment() {


    private lateinit var viewModel: MaleViewModel
    private lateinit var adapter: MaleClothsAdapter
    private lateinit var root: View
    private val indicatorColor: Int = R.color.white

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.male_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = GetMaleClothFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MaleViewModel::class.java)

        //check admin
        if (viewModel.currentUser() != null){
            root.add_male_wear.visibility = View.VISIBLE
            root.add_male_wear.setOnClickListener {
                Navigation.findNavController(root).navigate(R.id.navigation_addMaleCloth)
            }
        }else {
            root.add_male_wear.visibility = View.GONE
        }
        root.male_search_placeHolder.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_maleSearch)
        }
        root.spinKit.visibility = View.VISIBLE
        swipeRefresh()
        setChipSelection()
        displayCloths()
        return root
    }

    private fun displayCloths(){
        root.male_wear_recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getMaleCloths {wears->
            adapter = MaleClothsAdapter(wears)
            root.male_wear_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            root.spinKit.visibility = View.GONE
        }
    }

    private fun setChipSelection(){
        root.all.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_chipsAll)
        }
        root.trousers.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_chipsTrouser)
        }
        root.shirts.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_chipsShirt)
        }
    }

    private fun swipeRefresh(){
        root.maleSwipeRefresh.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark)
        root.maleSwipeRefresh.setColorSchemeResources(indicatorColor)
        root.maleSwipeRefresh.setOnRefreshListener{
            root.maleSwipeRefresh.isRefreshing = false
            shuffleItems()
        }
    }

    @Suppress("JavaCollectionsStaticMethodOnImmutableList")
    private fun shuffleItems(){
        viewModel.getMaleCloths {wears->
            Collections.shuffle(wears, Random(System.currentTimeMillis()))
            adapter = MaleClothsAdapter(wears)
            root.male_wear_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}