package com.gwc.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gwc.ui.auth.resetPassword.ResetPasswordViewModel

@Suppress("UNCHECKED_CAST")
class ResetPasswordFactory(private val app: Application)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResetPasswordViewModel(app) as T
    }

}