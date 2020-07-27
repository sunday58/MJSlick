package com.mjslick.ui.auth.logIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mjslick.R
import kotlinx.android.synthetic.main.log_in_fragment.*
import kotlinx.android.synthetic.main.log_in_fragment.view.*

class LogInFragment : Fragment() {

    private lateinit var viewModel: LogInViewModel
    private lateinit var textRegister: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.log_in_fragment, container, false)


        textRegister = root.findViewById(R.id.text_register)
        textRegister.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_register)
        }
        root.logIn.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_female)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
        // TODO: Use the ViewModel
    }

}