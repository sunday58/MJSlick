package com.mjslick.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mjslick.ui.female.AddFemaleClothViewModel

@Suppress("UNCHECKED_CAST")
class AddFemaleClothFactory(private val app: Application)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddFemaleClothViewModel(app) as T
    }

}