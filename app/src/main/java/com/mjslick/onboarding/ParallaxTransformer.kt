package com.mjslick.onboarding

import android.view.View
import android.widget.ImageView

import androidx.viewpager2.widget.ViewPager2
import com.mjslick.R


class ParallaxTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val absPosition = Math.abs(position)
        if (position < -1) {
            // This page is way off-screen to the left.
            view.alpha = 1f
        } else if (position <= 1) {
            val image = view.findViewById<ImageView>(R.id.onboarding_image)
            image?.apply {
                scaleX = 1.0f - absPosition * 2
                scaleY = 1.0f - absPosition * 2
                alpha = 1.0f - absPosition * 2
            }
            val shadow = view.findViewById<ImageView>(R.id.iv_shadow)
            shadow?.apply {
                scaleX = 1.0f - absPosition * 2
                scaleY = 1.0f - absPosition * 2
                alpha = 1.0f - absPosition * 2
            }
        } else {
            // This page is way off-screen to the right.
            view.alpha = 1f
        }
    }
}