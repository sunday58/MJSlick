package com.mjslick.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mjslick.ui.female.AddFemaleClothViewModel
import com.mjslick.ui.female.FemaleViewModel
import com.mjslick.ui.male.MaleViewModel

@Suppress("UNCHECKED_CAST")
class GetMaleClothFactory(private val app: Application)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MaleViewModel(app) as T
    }

}