package com.mjslick.ui.auth.register

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mjslick.R
import com.mjslick.model.User
import com.mjslick.ui.factory.ViewModelFactory
import com.mjslick.utility.Network
import kotlinx.android.synthetic.main.register_fragment.view.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.register_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)


        registerUser(root)
        return root
    }

   private fun registerUser(view: View) {
       view.register.setOnClickListener {

           if (Network.isOnline(requireContext())){
               showProgressBar(view)
           val userEmail = Objects.requireNonNull(view.register_email.text.toString())
           val userPassword = Objects.requireNonNull(view.register_password.text.toString())
           val userPhone = view.register_phoneNumber.text.toString()
           val userTwitter = view.register_twitter.text.toString()
           val userFacebook = view.register_facebook.text.toString()
           val userLinkedin = view.register_Linkedin.text.toString()

           val user = User(
               userEmail,
               userPhone,
               userTwitter,
               userFacebook,
               userLinkedin,
               listOf(),
               listOf()
           )

           if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
               Toast.makeText(
                   activity?.applicationContext, "Register email and password must not be empty",
                   Toast.LENGTH_SHORT
               ).show()
               hideProgressBar(view)
           } else if (!userEmail.contains("@")) {
               Toast.makeText(activity?.applicationContext, "Invalid email", Toast.LENGTH_SHORT)
                   .show()
               hideProgressBar(view)
           } else if (!isValidPassword(userPassword)) {
               Toast.makeText(
                   activity?.applicationContext, "Password should contain, letter and number",
                   Toast.LENGTH_SHORT
               ).show()
               hideProgressBar(view)
           } else {
               viewModel.register(userEmail, userPassword, user)
               Navigation.findNavController(view).navigate(R.id.navigation_logIn)
               hideProgressBar(view)
               Toast.makeText(requireContext(), "Registered Successful", Toast.LENGTH_SHORT).show()
           }
       }else {
               Toast.makeText(requireContext(), "Network not available", Toast.LENGTH_SHORT).show()
           }
   }

   }

   private fun isValidPassword(mPassword: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(mPassword)
        return matcher.matches()
    }

    private fun hideProgressBar(view: View) {
        view.spin_kit.visibility = View.GONE
    }
    private fun showProgressBar(view: View) {
        view.spin_kit.visibility = View.VISIBLE
    }
}