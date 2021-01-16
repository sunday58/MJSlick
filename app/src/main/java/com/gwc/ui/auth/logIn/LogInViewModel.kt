package com.gwc.ui.auth.logIn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository

class LogInViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()


    init {
        repository = Repository(firebaseSource)
    }

    fun login(email: String, password: String, result: (Task<AuthResult>) -> Unit) =
        repository.login(email, password){
        result(it)
    }

    fun currentUser() = repository.currentUser()

    fun logout() = repository.logout()
}