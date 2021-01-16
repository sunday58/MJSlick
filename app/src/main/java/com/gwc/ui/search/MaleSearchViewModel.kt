package com.gwc.ui.search

import androidx.lifecycle.ViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository
import com.gwc.model.MaleWear

class MaleSearchViewModel : ViewModel() {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun getMaleSearchCloths(name: String, myCallback: (List<MaleWear>) -> Unit) =
        repository.getMaleSearchCloths(name, myCallback)
}