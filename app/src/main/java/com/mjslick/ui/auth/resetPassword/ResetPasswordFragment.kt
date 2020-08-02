package com.mjslick.ui.auth.resetPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.mjslick.R
import com.mjslick.ui.factory.ResetPasswordFactory
import kotlinx.android.synthetic.main.fragment_reset_password.*
import kotlinx.android.synthetic.main.fragment_reset_password.view.*
import java.util.*


class ResetPasswordFragment : DialogFragment() {

    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val root =  inflater.inflate(R.layout.fragment_reset_password, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ResetPasswordFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResetPasswordViewModel::class.java)

        resetPassword(root)
        return root
    }

    private fun resetPassword(view: View) {

       view.passwordUpdate.setOnClickListener {

             val email = Objects.requireNonNull(view.passwordResetText.text.toString())

            if (!email.contains("@") || email.isEmpty()){
                Toast.makeText(requireContext(), "check email", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.resetPassword(email)
                dialog?.dismiss()
            }
        }
    }

}