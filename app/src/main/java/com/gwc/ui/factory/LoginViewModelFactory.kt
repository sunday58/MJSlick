package com.gwc.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gwc.ui.auth.logIn.LogInViewModel

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val app: Application)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LogInViewModel(app) as T
    }

}