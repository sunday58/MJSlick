package com.gwc.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gwc.ui.male.MaleViewModel

@Suppress("UNCHECKED_CAST")
class GetMaleClothFactory(private val app: Application)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MaleViewModel(app) as T
    }

}