package com.mjslick.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mjslick.R
import com.mjslick.model.LadiesWear
import kotlinx.android.synthetic.main.fragment_detail_cloth.view.*


class DetailClothFragment : Fragment() {

    private lateinit var root: View
    private lateinit var ladiesWear: LadiesWear

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_detail_cloth, container, false)

        if (arguments != null && requireArguments().containsKey("ladies")){
            ladiesWear = requireArguments().getSerializable("ladies") as LadiesWear

            getImage(ladiesWear.clothImages[0])
            root.femaleWearDescription.text = ladiesWear.clothDescription
            root.detail_top_price.text = ladiesWear.topPrice
            root.detail_trouser_price.text = ladiesWear.trouserPrice
            root.detail_complete_price.text = ladiesWear.completePrice
        }

        return root
    }

    private fun getImage(url: String){
        Glide.with(requireContext())
            .load(url)
            .into(root.meal_image)
    }

}