package com.mjslick.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.mjslick.R
import com.mjslick.storage.SharedPreferenceStorage
import com.mjslick.utility.Constants
import kotlinx.android.synthetic.main.onboarding_fragment.*

class OnBoardingFragment : Fragment() {

    private lateinit var mOnBoardingViewModel: OnBoardingViewModel
    private lateinit var  mStorage: SharedPreferenceStorage
    private lateinit var mNavController: NavController
    private lateinit var modelList: ArrayList<OnBoardingModel>
    private lateinit var mOnBoardingViewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.onboarding_fragment, container, false)
        mOnBoardingViewPager = root.findViewById(R.id.onBoarding_viewPager)
        mStorage = SharedPreferenceStorage(root.context)
        val pageTransformer = ParallaxTransformer()
        mOnBoardingViewPager.setPageTransformer(pageTransformer)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mNavController = Navigation.findNavController(view)
        //set nav controller
        mOnBoardingViewModel = ViewModelProvider(this).get(
            OnBoardingViewModel::class.java
        )

        mOnBoardingViewModel.seenOnBoardingScreen(mStorage.hasSeenOnBoardingScreen(Constants.ON_BOARDING_KEY))
        mOnBoardingViewModel.mUserStatesMutableLiveData.observe(viewLifecycleOwner,
            Observer { userStates: OnBoardingViewModel.UserStates? ->
            when (userStates) {
                OnBoardingViewModel.UserStates.SEEN_ON_BOARDING_SCREEN -> mNavController.navigate(R.id.navigation_logIn)
                OnBoardingViewModel.UserStates.NOT_SEEN_ON_BOARDING_SCREEN -> notSeenOnBoarding()
            }
        }
        )
    }

    private fun notSeenOnBoarding(){

        modelList = ArrayList()

        modelList.add(
            OnBoardingModel(
                R.drawable.onboarding_icon1,
                resources.getString(R.string.onBoardingTitle1),
                resources.getString(R.string.onBoardingText1)
                )
        )
        modelList.add(
            OnBoardingModel(
                R.drawable.onboarding_icon2,
                resources.getString(R.string.onBoardingTitle2),
                resources.getString(R.string.onBoardingText2))
        )
        modelList.add(
            OnBoardingModel(
                R.drawable.onboarding_icon3,
                resources.getString(R.string.onBoardingTitle3),
                resources.getString(R.string.onBoardingText3))
        )

        //set Adapter
        val adapter = OnBoardingScreenAdapter(modelList)
        mOnBoardingViewPager.adapter = adapter

        view.apply {
            button_prev.setOnClickListener {
                mOnBoardingViewPager.setCurrentItem(mOnBoardingViewPager.currentItem - 1,
                    true)
            }
            button_next.setOnClickListener {
                if (mOnBoardingViewPager.currentItem < 2)
                    mOnBoardingViewPager.setCurrentItem(mOnBoardingViewPager.currentItem + 1, true)
                else {
                    mNavController.navigate(R.id.navigation_logIn)
                    mOnBoardingViewModel.seenOnBoardingScreen(true)
                    mStorage.setSeenOnBoardingScreen(Constants.ON_BOARDING_KEY, true)
                }
            }

            mOnBoardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {

                    when (position) {
                        0 -> {
                            hideButton(button_prev)
                            text_next.setText(R.string.next_onboarding_text)
                            indicatorSelector(first = true, second = false, third = false)
                        }
                        1 -> {
                            showButton(button_prev)
                            showButton(button_next)
                            text_next.setText(R.string.next_onboarding_text)
                            indicatorSelector(first = false, second = true, third = false)
                        }
                        2 -> {
                            showButton(button_prev)
                            text_next.setText(R.string.start_onboarding_text)
                            indicatorSelector(first = false, second = false, third = true)
                        }
                    }
                }
            })
        }
    }

private fun showButton(appCompatButton: MaterialCardView) {
    appCompatButton.visibility = View.VISIBLE
}

private fun hideButton(appCompatButton: MaterialCardView) {
    appCompatButton.visibility = View.GONE
}

private fun indicatorSelector(first: Boolean, second: Boolean, third: Boolean) {
    indicator1.isSelected = first
    indicator2.isSelected = second
    indicator3.isSelected = third
}

}