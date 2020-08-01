package com.mjslick.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mjslick.R
import com.mjslick.ui.factory.ViewModelFactory
import kotlinx.android.synthetic.main.register_fragment.view.*

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
        return root
    }

   private fun registerUser(view: View){

       val userEmail = view.register_email.text
       val userPassword = view.register_password.text
       val userPhone  = view.register_phoneNumber.text
       val userTwitter = view.register_twitter.text
       val userFacebook = view.register_facebook.text
       val userLinkedin = view.register_Linkedin.text



   }

}