package com.mjslick.ui.profile

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mjslick.R
import com.mjslick.utility.Constants
import kotlinx.android.synthetic.main.profile_fragment.view.*

class ProfileFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.profile_fragment, container, false)
        root.goBack.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.navigation_female)
        }
        shareOnPlayStore()
        call()
        sendEmail()
        openTwitter()
        openFacebook()
        return root
    }

    private fun shareOnPlayStore(){
        root.share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, Constants.PLAYSTORE_ACCOUNT)
            intent.type = "text/plain"

            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }
    }
    private fun sendEmail(){
        root.user_email.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", Constants.EMAIL_ADDRESS, null
            ))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            startActivity(Intent.createChooser(intent, "send email..."))
        }
    }

    private fun call(){
        root.user_phoneNumber.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_DIAL, Uri.parse("tel:" +
                    Constants.PHONE_NUMBER))
            startActivity(intent)
        }
    }

    private fun openTwitter(){
        root.user_twitterAccount.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TWITTER_URL))
                startActivity(intent)
            }catch (e: Exception){
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                    Uri.parse(Constants.TWITTER_WEB_URL))
                )
            }
        }
    }

    private fun openFacebook(){
        root.user_facebookAccount.setOnClickListener {
            val facebookIntent = Intent(Intent.ACTION_VIEW)
            val facebookUrl = getFacebookPageUrl(requireParentFragment().requireContext())
            facebookIntent.data = Uri.parse(facebookUrl)
            startActivity(facebookIntent)
        }
    }

    private fun getFacebookPageUrl(context: Context): String{
        val packageManager = context.packageManager
        try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana",
                0).versionCode
            if (versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + Constants.FACEBOOK_URL
            }else {
                return "fb://page/" + Constants.FACEBOOK_PAGE_ID
            }
        }catch (e: PackageManager.NameNotFoundException){
            return Constants.FACEBOOK_URL
        }
    }

}