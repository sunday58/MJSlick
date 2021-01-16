package com.gwc.onboarding


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gwc.R



class OnBoardingScreenAdapter( private  val onBoardingList: List<OnBoardingModel> ):
    RecyclerView.Adapter<OnBoardingScreenAdapter.OnBoardingViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.onboarding_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return onBoardingList.size
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {

        holder.onBoardingImage.setImageResource(onBoardingList[position].onBoardingImage)
        holder.onBoardingTitle.text = onBoardingList[position].onBoardingTextTitle
        holder.onBoardingDesc.text = onBoardingList[position].onBoardingTextDescription
    }

    class OnBoardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val onBoardingImage: ImageView = itemView.findViewById(R.id.onboarding_image)
        val onBoardingTitle: TextView = itemView.findViewById(R.id.onboarding_textTitle)
        val onBoardingDesc: TextView = itemView.findViewById(R.id.onboarding_textDescription)
    }
}