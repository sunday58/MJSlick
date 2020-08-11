package com.mjslick.ui.search

import androidx.lifecycle.ViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository
import com.mjslick.model.LadiesWear

class FemaleSearchViewModel : ViewModel() {
    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun getFemaleSearchCloths(name: String, myCallback: (List<LadiesWear>) -> Unit) =
        repository.getFemaleSearchCloths(name, myCallback)
}