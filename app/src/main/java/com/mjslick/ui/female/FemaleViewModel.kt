package com.mjslick.ui.female

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository
import com.mjslick.model.LadiesWear

class FemaleViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()


    init {
        repository = Repository(firebaseSource)
    }

    fun getFemaleCloths(myCallback: (List<LadiesWear>) -> Unit) = repository.getFemaleCloths(myCallback)

    fun currentUser() = repository.currentUser()
}