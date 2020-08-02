package com.mjslick.ui.auth.logIn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository

class LogInViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()


    init {
        repository = Repository(firebaseSource)
    }

    fun login(email: String, password: String) = repository.login(email, password)

    fun currentUser() = repository.currentUser()

    fun logout() = repository.logout()
}