package com.gwc.ui.female

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository
import com.gwc.model.LadiesWear

class FemaleViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()


    init {
        repository = Repository(firebaseSource)
    }

    fun getFemaleCloths(myCallback: (List<LadiesWear>) -> Unit) = repository.getFemaleCloths(myCallback)

    fun currentUser() = repository.currentUser()
}