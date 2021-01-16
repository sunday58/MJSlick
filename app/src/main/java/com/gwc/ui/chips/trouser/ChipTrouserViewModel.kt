package com.gwc.ui.chips.trouser

import androidx.lifecycle.ViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository
import com.gwc.model.MaleWear

class ChipTrouserViewModel : ViewModel() {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }
    fun getMaleTrousers(myCallback: (List<MaleWear>) -> Unit) = repository.getMaleTrousers(myCallback)
}