package com.mjslick.ui.chips.trouser

import androidx.lifecycle.ViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository
import com.mjslick.model.MaleWear

class ChipTrouserViewModel : ViewModel() {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }
    fun getMaleTrousers(myCallback: (List<MaleWear>) -> Unit) = repository.getMaleTrousers(myCallback)
}