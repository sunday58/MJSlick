package com.mjslick.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mjslick.database.Repository
import com.mjslick.ui.auth.register.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val app: Application)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(app) as T
    }

}