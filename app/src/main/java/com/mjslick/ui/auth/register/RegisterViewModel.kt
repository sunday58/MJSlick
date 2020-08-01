package com.mjslick.ui.auth.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mjslick.database.Repository
import com.mjslick.model.User


class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: Repository

    init {
        repository.currentUser()
    }

        fun register(email: String, password: String, user: User) {
            repository.register(email, password, user)
        }

        fun currentUser() {
            repository.currentUser()
        }
}