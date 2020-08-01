package com.mjslick.ui.auth.register

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mjslick.R
import com.mjslick.model.User
import com.mjslick.ui.factory.ViewModelFactory
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.register_fragment.view.*
import java.util.*

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

   private fun registerUser(view: View){
       view.register.setOnClickListener {
           view.spin_kit.visibility = View.VISIBLE

           val userEmail = Objects.requireNonNull(view.register_email.text.toString())
           val userPassword = Objects.requireNonNull(view.register_password.text.toString())
           val userPhone  = view.register_phoneNumber.text.toString()
           val userTwitter = view.register_twitter.text.toString()
           val userFacebook = view.register_facebook.text.toString()
           val userLinkedin = view.register_Linkedin.text.toString()

           val user = User(userEmail, userPhone, userTwitter, userFacebook, userLinkedin, listOf(), listOf())

           if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
               Toast.makeText(activity?.applicationContext, "Register email and password must not be empty",
                   Toast.LENGTH_SHORT).show()
               view.spin_kit.visibility = View.GONE
           }else if (!userEmail.contains("@")){
               Toast.makeText(activity?.applicationContext, "Invalid email", Toast.LENGTH_SHORT).show()
               view.spin_kit.visibility = View.GONE
           }else {
               viewModel.register(userEmail, userPassword, user)
               toastMessage(viewModel.message(""))
                Navigation.findNavController(view).navigate(R.id.navigation_logIn)
               view.spin_kit.visibility = View.GONE
               Toast.makeText(requireContext(), "Registered Successful", Toast.LENGTH_SHORT).show()
           }
       }

   }
   private fun toastMessage(toastMessage: String) {
        Toast.makeText(activity?.applicationContext, toastMessage, Toast.LENGTH_SHORT).show()
    }

}