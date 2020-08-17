package com.mjslick.ui.detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mjslick.R
import com.mjslick.model.LadiesWear
import com.mjslick.model.MaleWear
import com.mjslick.utility.Constants
import kotlinx.android.synthetic.main.fragment_detail_cloth.view.*
import kotlin.system.exitProcess


class DetailClothFragment : Fragment() {

    private lateinit var root: View
    private lateinit var ladiesWear: LadiesWear
    private lateinit var maleWear: MaleWear
    private lateinit var viewModel: DetailClothViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_detail_cloth, container, false)
        viewModel = ViewModelProvider(this).get(DetailClothViewModel::class.java)

        //check admin
        if (viewModel.currentUser() != null){
            root.delete.visibility = View.VISIBLE
        }else {
            root.delete.visibility = View.GONE
        }

        if (arguments != null && requireArguments().containsKey("ladies")){
            ladiesWear = requireArguments().getSerializable("ladies") as LadiesWear

            getImage(ladiesWear.clothImages[0])
            root.wear_detailTitle.text = ladiesWear.clothName
            root.femaleWearDescription.text = ladiesWear.clothDescription
            root.detail_top_price.text = ladiesWear.topPrice
            root.detail_trouser_price.text = ladiesWear.trouserPrice
            root.detail_complete_price.text = ladiesWear.completePrice

            deleteFemaleCloth(ladiesWear.key)

            //check null contents
            when {
                ladiesWear.topPrice.length < 2 && ladiesWear.trouserPrice.length < 2 -> {
                    root.detail_top_price.visibility = View.GONE
                    root.top_price.visibility = View.GONE
                    root.trouser.visibility = View.GONE
                    root.detail_trouser_price.visibility = View.GONE
                }
                ladiesWear.topPrice.length < 2 && ladiesWear.completePrice.length < 2 -> {
                    root.detail_top_price.visibility = View.GONE
                    root.top_price.visibility = View.GONE
                    root.complete.visibility = View.GONE
                    root.detail_complete_price.visibility = View.GONE
                }
                ladiesWear.trouserPrice.length < 2 &&  ladiesWear.completePrice.length < 2 -> {
                    root.trouser.visibility = View.GONE
                    root.detail_trouser_price.visibility = View.GONE
                    root.complete.visibility = View.GONE
                    root.detail_complete_price.visibility = View.GONE
                }
                ladiesWear.topPrice.length < 2 -> {
                    root.detail_top_price.visibility = View.GONE
                    root.top_price.visibility = View.GONE
                }
                ladiesWear.trouserPrice.length < 2 -> {
                    root.trouser.visibility = View.GONE
                    root.detail_trouser_price.visibility = View.GONE
                }
                ladiesWear.completePrice.length < 2 -> {
                    root.complete.visibility = View.GONE
                    root.detail_complete_price.visibility = View.GONE
                }
                else -> {
                    root.detail_top_price.visibility = View.VISIBLE
                    root.top_price.visibility = View.VISIBLE
                    root.trouser.visibility = View.VISIBLE
                    root.detail_trouser_price.visibility = View.VISIBLE
                    root.complete.visibility = View.VISIBLE
                    root.detail_complete_price.visibility = View.VISIBLE
                }
            }

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

            deleteMaleCloth(maleWear.key)

            //check null contents
            when {
                maleWear.topPrice.length < 2 && maleWear.trouserPrice.length < 2 -> {
                    root.detail_top_price.visibility = View.GONE
                    root.top_price.visibility = View.GONE
                    root.trouser.visibility = View.GONE
                    root.detail_trouser_price.visibility = View.GONE
                }
                maleWear.topPrice.length < 2 && maleWear.completePrice.length < 2 -> {
                    root.detail_top_price.visibility = View.GONE
                    root.top_price.visibility = View.GONE
                    root.complete.visibility = View.GONE
                    root.detail_complete_price.visibility = View.GONE
                }
                maleWear.trouserPrice.length < 2 &&  maleWear.completePrice.length < 2 -> {
                    root.trouser.visibility = View.GONE
                    root.detail_trouser_price.visibility = View.GONE
                    root.complete.visibility = View.GONE
                    root.detail_complete_price.visibility = View.GONE
                }
                maleWear.topPrice.length < 2 -> {
                    root.detail_top_price.visibility = View.GONE
                    root.top_price.visibility = View.GONE
                }
                maleWear.trouserPrice.length < 2 -> {
                    root.trouser.visibility = View.GONE
                    root.detail_trouser_price.visibility = View.GONE
                }
                maleWear.completePrice.length < 2 -> {
                    root.complete.visibility = View.GONE
                    root.detail_complete_price.visibility = View.GONE
                }
                else -> {
                    root.detail_top_price.visibility = View.VISIBLE
                    root.top_price.visibility = View.VISIBLE
                    root.trouser.visibility = View.VISIBLE
                    root.detail_trouser_price.visibility = View.VISIBLE
                    root.complete.visibility = View.VISIBLE
                    root.detail_complete_price.visibility = View.VISIBLE
                }
            }

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
        openLinkedin()
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

    private fun openLinkedin(){
        root.detail_linkedin.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(Constants.LINKEDIN_URL)
                intent.setPackage("com.linkedin.android")
                startActivity(intent)
            }catch (e: ActivityNotFoundException){
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.LINKEDIN_URL)))
            }
        }
    }

    private fun deleteMaleCloth(key: String){

        root.delete.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(requireContext())
            dialog.setTitle("Deleting?")
            dialog.setIcon(R.drawable.ic_baseline_delete_forever_24)
            dialog.setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(
                    "YES"
                ) { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                    val popupMenu = PopupMenu(requireContext(), it)
                    popupMenu.setOnMenuItemClickListener {item ->
                        when(item.itemId) {
                            R.id.menu_delete -> {
                                viewModel.deleteMaleCloth(key)
                                true
                            }
                            else -> false
                        }
                    }
                    popupMenu.inflate(R.menu.admin_menu)

                    try {
                        val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                        fieldMPopup.isAccessible = true
                        val mPopup = fieldMPopup.get(popupMenu)
                        mPopup.javaClass
                            .getDeclaredMethod("setForceShowIcon", Boolean::class.java )
                            .invoke(mPopup, true)
                    }catch (e: Exception) {
                        Log.e("Menu", "Error showing menu icons.", e)
                    } finally {
                        popupMenu.show()
                    }

                }
                .setNegativeButton(
                    "NO"
                ) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
            dialog.create().show()
        }
    }

    private fun deleteFemaleCloth(key: String){

        root.delete.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(requireContext())
            dialog.setTitle("Deleting?")
            dialog.setIcon(R.drawable.ic_close)
            dialog.setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(
                    "YES"
                ) { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                    val popupMenu = PopupMenu(requireContext(), it)
                    popupMenu.setOnMenuItemClickListener {item ->
                        when(item.itemId) {
                            R.id.menu_delete -> {
                                viewModel.deleteFemaleCloth(key)
                                true
                            }
                            else -> false
                        }
                    }
                    popupMenu.inflate(R.menu.admin_menu)

                    try {
                        val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                        fieldMPopup.isAccessible = true
                        val mPopup = fieldMPopup.get(popupMenu)
                        mPopup.javaClass
                            .getDeclaredMethod("setForceShowIcon", Boolean::class.java )
                            .invoke(mPopup, true)
                    }catch (e: Exception) {
                        Log.e("Menu", "Error showing menu icons.", e)
                    } finally {
                        popupMenu.show()
                    }

                }
                .setNegativeButton(
                    "NO"
                ) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
            dialog.create().show()
        }
    }
}