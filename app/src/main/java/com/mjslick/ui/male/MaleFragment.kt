package com.mjslick.ui.male

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mjslick.R
import com.mjslick.ui.factory.GetFemaleClothFactory
import com.mjslick.ui.factory.GetMaleClothFactory
import com.mjslick.ui.female.FemaleViewModel
import kotlinx.android.synthetic.main.male_fragment.view.*

class MaleFragment : Fragment() {


    private lateinit var viewModel: MaleViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.male_fragment, container, false)


        val application = requireNotNull(this.activity).application
        val viewModelFactory = GetMaleClothFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MaleViewModel::class.java)

        root.add_male_wear.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_addMaleCloth)
        }
        return root
    }

}