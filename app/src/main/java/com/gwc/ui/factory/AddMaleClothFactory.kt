package com.gwc.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gwc.ui.male.AddMaleClothViewModel

@Suppress("UNCHECKED_CAST")
class AddMaleClothFactory(private val app: Application)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddMaleClothViewModel(app) as T
    }

}