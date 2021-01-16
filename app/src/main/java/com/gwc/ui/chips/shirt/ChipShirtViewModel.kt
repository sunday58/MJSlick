package com.gwc.ui.chips.shirt

import androidx.lifecycle.ViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository
import com.gwc.model.MaleWear

class ChipShirtViewModel : ViewModel() {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }
    fun getMaleShirts(myCallback: (List<MaleWear>) -> Unit) = repository.getMaleShirts(myCallback)
}