package com.gwc.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gwc.ui.female.FemaleViewModel

@Suppress("UNCHECKED_CAST")
class GetFemaleClothFactory(private val app: Application)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FemaleViewModel(app) as T
    }

}