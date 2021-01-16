package com.gwc.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwc.R
import com.gwc.model.LadiesWear

class FemaleClothsAdapter(private val clothList: List<LadiesWear>)
    :RecyclerView.Adapter<FemaleClothsAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.female_wear_list_item,
            parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = clothList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data = clothList[position]
        holder.loadImage(data.clothImages[0])
        holder.femaleWearTitle.text = data.clothName
        holder.femaleWearRating.numStars = 5
        holder.femaleWearRating.rating = 4F

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("ladies", data)
            Navigation.findNavController(holder.itemView).navigate(R.id.navigation_detail, bundle)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         val femaleWearTitle: TextView = itemView.findViewById(R.id.female_wear_title)
         val femaleWearRating: RatingBar = itemView.findViewById(R.id.female_wear_rating)
        private val femaleWearImage: ImageView = itemView.findViewById(R.id.ladies_wear_image)

        fun loadImage(image: String) {
            Glide.with(itemView.context)
                .load(image)
                .into(femaleWearImage)
        }

    }


}