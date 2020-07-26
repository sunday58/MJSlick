package com.mjslick.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingViewModel : ViewModel() {
    enum class UserStates {
        SEEN_ON_BOARDING_SCREEN, NOT_SEEN_ON_BOARDING_SCREEN
    }

    val mUserStatesMutableLiveData = MutableLiveData<UserStates>()

    fun OnBoardingViewModel() {
        mUserStatesMutableLiveData.value = UserStates.NOT_SEEN_ON_BOARDING_SCREEN
    }

    fun seenOnBoardingScreen(seenOnBoardingScreen: Boolean) {
        if (!seenOnBoardingScreen) {
            mUserStatesMutableLiveData.setValue(UserStates.NOT_SEEN_ON_BOARDING_SCREEN)
        } else {
            mUserStatesMutableLiveData.setValue(UserStates.SEEN_ON_BOARDING_SCREEN)
        }
    }
}