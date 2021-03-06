package com.gwc.ui.auth.resetPassword

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository

class ResetPasswordViewModel(application: Application): AndroidViewModel(application) {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun resetPassword(email: String) = repository.resetPassword(email)
}