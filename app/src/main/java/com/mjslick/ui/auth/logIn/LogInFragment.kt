package com.mjslick.ui.auth.logIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mjslick.R
import com.mjslick.ui.factory.LoginViewModelFactory
import com.mjslick.utility.Network
import kotlinx.android.synthetic.main.log_in_fragment.view.*
import java.util.*

class LogInFragment : Fragment() {

    private lateinit var viewModel: LogInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.log_in_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = LoginViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LogInViewModel::class.java)

       root.text_register.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_register)
        }
        viewModel.logout()
        logIn(root)
        return root
    }

    private fun logIn(view: View) {
        view.logIn.setOnClickListener {

            if (Network.isOnline(requireContext())){

                showProgressBar(view)
            val email = Objects.requireNonNull(view.logIn_email.text.toString())
            val password = Objects.requireNonNull(view.logIn_password.text.toString())

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    activity?.applicationContext, "login email and password must not be empty",
                    Toast.LENGTH_SHORT
                ).show()
                hideProgressBar(view)
            } else if (!email.contains("@")) {
                Toast.makeText(
                    activity?.applicationContext, "Invalid email",
                    Toast.LENGTH_SHORT
                ).show()
                hideProgressBar(view)
            } else {
                viewModel.login(email, password)
                if (viewModel.currentUser() != null)
                    Navigation.findNavController(view).navigate(R.id.navigation_female)
                hideProgressBar(view)
            }
        }else {
                Toast.makeText(requireContext(), "Network not available", Toast.LENGTH_SHORT).show()
            }
    }
    }

    private fun hideProgressBar(view: View) {
        view.logInSpinKit.visibility = View.GONE
    }
    private fun showProgressBar(view: View) {
        view.logInSpinKit.visibility = View.VISIBLE
    }

}