package com.mjslick.ui.adapters

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
import com.mjslick.model.MaleWear

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
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         val maleWearTitle: TextView = itemView.findViewById(R.id.male_wear_title)
         val maleWearRating: RatingBar = itemView.findViewById(R.id.male_wear_rating)
        private val maleWearImage: ImageView = itemView.findViewById(R.id.male_wear_image)

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, adapterPosition.toString(),
                    Toast.LENGTH_SHORT).show()
            }
        }

        fun loadImage(image: String) {
            Glide.with(itemView.context)
                .load(image)
                .into(maleWearImage)
        }

    }


}