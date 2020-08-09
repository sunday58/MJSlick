package com.mjslick.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mjslick.R
import kotlinx.android.synthetic.main.fragment_zoom_image.*


class ZoomImageFragment : Fragment() {


    private lateinit var ladiesImage: String
    private lateinit var maleImage: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_zoom_image, container, false)

        if (arguments != null && requireArguments().containsKey("femaleImage")){
            ladiesImage = requireArguments().getString("femaleImage") as String
            getZoomImage(ladiesImage)
        }else {
            maleImage = requireArguments().getString("maleImage") as String
            getZoomImage(maleImage)
        }

        return root
    }

    private fun getZoomImage(url: String){
        Glide.with(requireContext())
            .load(url)
            .into(image_zoom)
    }

}