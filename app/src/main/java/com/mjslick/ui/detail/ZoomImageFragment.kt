package com.mjslick.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mjslick.R
import com.mjslick.model.LadiesWear
import com.mjslick.model.MaleWear
import kotlinx.android.synthetic.main.fragment_zoom_image.*
import kotlinx.android.synthetic.main.fragment_zoom_image.view.*


class ZoomImageFragment : Fragment() {

    private lateinit var ladiesImage: String
    private lateinit var maleImage: String
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_zoom_image, container, false)

        if (arguments != null && requireArguments().containsKey("woman")){
            ladiesImage = requireArguments().getString("woman") as String
            getZoomImage(ladiesImage)
        }
        else if (arguments != null && requireArguments().containsKey("man") ) {
            maleImage = requireArguments().getString("man") as String
            getZoomImage(maleImage)
        }

        return root
    }

    private fun getZoomImage(url: String){
        Glide.with(requireContext())
            .load(url)
            .into(root.image_zoom)
    }
}