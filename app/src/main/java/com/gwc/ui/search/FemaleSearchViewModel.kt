package com.gwc.ui.search

import androidx.lifecycle.ViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository
import com.gwc.model.LadiesWear

class FemaleSearchViewModel : ViewModel() {
    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun getFemaleSearchCloths(name: String, myCallback: (List<LadiesWear>) -> Unit) =
        repository.getFemaleSearchCloths(name, myCallback)
}