package com.mjslick.ui.detail

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mjslick.R
import com.mjslick.model.LadiesWear
import com.mjslick.model.MaleWear
import com.mjslick.utility.Constants
import kotlinx.android.synthetic.main.fragment_detail_cloth.view.*


class DetailClothFragment : Fragment() {

    private lateinit var root: View
    private lateinit var ladiesWear: LadiesWear
    private lateinit var maleWear: MaleWear

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_detail_cloth, container, false)

        if (arguments != null && requireArguments().containsKey("ladies")){
            ladiesWear = requireArguments().getSerializable("ladies") as LadiesWear

            getImage(ladiesWear.clothImages[0])
            root.wear_detailTitle.text = ladiesWear.clothName
            root.femaleWearDescription.text = ladiesWear.clothDescription
            root.detail_top_price.text = ladiesWear.topPrice
            root.detail_trouser_price.text = ladiesWear.trouserPrice
            root.detail_complete_price.text = ladiesWear.completePrice

            root.detail_image.setOnClickListener {
                val zoomImageFragment = ZoomImageFragment()
                val bundle = Bundle()
                bundle.putString("woman", ladiesWear.clothImages[0])
                zoomImageFragment.arguments = bundle
                Navigation.findNavController(root).navigate(R.id.navigation_zoom, bundle)
            }

        }else{
            maleWear = requireArguments().getSerializable("male") as MaleWear
            getImage(maleWear.clothImages[0])
            root.wear_detailTitle.text = maleWear.clothName
            root.femaleWearDescription.text = maleWear.clothDescription
            root.detail_top_price.text = maleWear.topPrice
            root.detail_trouser_price.text = maleWear.trouserPrice
            root.detail_complete_price.text = maleWear.completePrice

            root.detail_image.setOnClickListener {
                val zoomImageFragment = ZoomImageFragment()
                val bundle = Bundle()
                bundle.putString("man", maleWear.clothImages[0])
                zoomImageFragment.arguments = bundle
                Navigation.findNavController(root).navigate(R.id.navigation_zoom, bundle)
            }
        }
        call()
        sendEmail()
        openTwitter()
        openFacebook()
        return root
    }

    private fun getImage(url: String){
        Glide.with(requireContext())
            .load(url)
            .into(root.detail_image)
    }

    private fun sendEmail(){
        root.detail_email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", Constants.EMAIL_ADDRESS, null
            ))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            startActivity(Intent.createChooser(intent, "send email..."))
        }
    }

    private fun call(){
        root.detail_phoneNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
            Constants.PHONE_NUMBER))
            startActivity(intent)
        }
    }

    private fun openTwitter(){
        root.detail_twitter.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TWITTER_URL))
                startActivity(intent)
            }catch (e: Exception){
                startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse(Constants.TWITTER_WEB_URL)))
            }
        }
    }

    private fun openFacebook(){
        root.detail_facebook.setOnClickListener {
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