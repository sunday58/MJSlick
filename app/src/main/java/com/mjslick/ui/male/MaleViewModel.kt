package com.mjslick.ui.male

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository
import com.mjslick.model.MaleWear

class MaleViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun getMaleCloths(myCallback: (List<MaleWear>) -> Unit) = repository.getMaleCloths(myCallback)

    fun currentUser() = repository.currentUser()
}