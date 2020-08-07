package com.mjslick.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mjslick.R
import com.mjslick.model.LadiesWear

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
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         val femaleWearTitle: TextView = itemView.findViewById(R.id.female_wear_title)
         val femaleWearRating: RatingBar = itemView.findViewById(R.id.female_wear_rating)
        private val femaleWearImage: ImageView = itemView.findViewById(R.id.ladies_wear_image)

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, clothList[adapterPosition].toString(),
                    Toast.LENGTH_SHORT).show()
            }
        }

        fun loadImage(image: String) {
            Glide.with(itemView.context)
                .load(image)
                .into(femaleWearImage)
        }

    }


}