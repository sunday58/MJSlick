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

class MaleFragment : Fragment() {


    private lateinit var viewModel: MaleViewModel
    private lateinit var adapter: MaleClothsAdapter
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.male_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = GetMaleClothFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MaleViewModel::class.java)

        root.add_male_wear.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_addMaleCloth)
        }
        displayCloths()
        return root
    }

    private fun displayCloths(){
        root.male_wear_recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getMaleCloths {wears->
            adapter = MaleClothsAdapter(wears)
            root.male_wear_recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }

}