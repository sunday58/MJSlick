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
import com.gwc.model.MaleWear

class MaleClothsAdapter(private val clothList: List<MaleWear>)
    :RecyclerView.Adapter<MaleClothsAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.male_wear_list_item,
            parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = clothList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data = clothList[position]
        holder.loadImage(data.clothImages[0])
        holder.maleWearTitle.text = data.clothName
        holder.maleWearRating.numStars = 5
        holder.maleWearRating.rating = 4F

       holder.itemView.setOnClickListener {
           val bundle = Bundle()
           bundle.putSerializable("male", data)
           Navigation.findNavController(holder.itemView).navigate(R.id.navigation_detail, bundle)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         val maleWearTitle: TextView = itemView.findViewById(R.id.male_wear_title)
         val maleWearRating: RatingBar = itemView.findViewById(R.id.male_wear_rating)
        private val maleWearImage: ImageView = itemView.findViewById(R.id.male_wear_image)


        fun loadImage(image: String) {
            Glide.with(itemView.context)
                .load(image)
                .into(maleWearImage)
        }

    }


}