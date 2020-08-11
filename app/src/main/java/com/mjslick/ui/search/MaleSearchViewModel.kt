package com.mjslick.ui.search

import androidx.lifecycle.ViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository
import com.mjslick.model.MaleWear

class MaleSearchViewModel : ViewModel() {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun getMaleSearchCloths(name: String, myCallback: (List<MaleWear>) -> Unit) =
        repository.getMaleSearchCloths(name, myCallback)
}