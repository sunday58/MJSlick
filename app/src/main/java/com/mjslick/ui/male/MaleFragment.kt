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
import kotlinx.android.synthetic.main.male_fragment.view.*

class MaleFragment : Fragment() {

    companion object {
        fun newInstance() = MaleFragment()
    }

    private lateinit var viewModel: MaleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.male_fragment, container, false)

        root.add_male_wear.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_addMaleCloth)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MaleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}