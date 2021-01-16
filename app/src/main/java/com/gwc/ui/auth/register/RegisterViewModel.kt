package com.gwc.ui.auth.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository
import com.gwc.model.User


class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()


    init {
        repository = Repository(firebaseSource)
    }

        fun register(email: String, password: String, user: User) {
            repository.register(email, password, user)
        }

        fun currentUser() {
            repository.currentUser()
        }
}