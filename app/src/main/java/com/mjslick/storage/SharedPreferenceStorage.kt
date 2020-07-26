package com.mjslick.storage

import android.content.Context
import android.content.SharedPreferences
import com.mjslick.utility.Constants


class SharedPreferenceStorage(context: Context) {
    private var mPreferences: SharedPreferences? = null

    init {
        mPreferences = context.getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
    }

    fun setSeenOnBoardingScreen(key: String?, value: Boolean) {
        val pref = mPreferences!!.edit()
        pref.putBoolean(key, value)
        pref.apply()
    }

    fun hasSeenOnBoardingScreen(key: String?): Boolean {
        return mPreferences!!.getBoolean(key, false)
    }

    fun isRememberMeChecked(key: String?): Boolean {
        return mPreferences!!.getBoolean(key, false)
    }
}