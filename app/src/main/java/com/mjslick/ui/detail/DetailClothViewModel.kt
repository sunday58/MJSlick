package com.mjslick.ui.detail

import androidx.lifecycle.ViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository

class DetailClothViewModel: ViewModel() {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun deleteMaleCloth(key: String) =
        repository.deleteMaleCloth(key)

    fun deleteFemaleCloth(key: String) =
        repository.deleteFemaleCloth(key)

    fun currentUser() = repository.currentUser()
}