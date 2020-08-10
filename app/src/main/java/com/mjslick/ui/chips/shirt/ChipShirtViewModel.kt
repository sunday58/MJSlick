package com.mjslick.ui.chips.shirt

import androidx.lifecycle.ViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository
import com.mjslick.model.MaleWear

class ChipShirtViewModel : ViewModel() {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }
    fun getMaleShirts(myCallback: (List<MaleWear>) -> Unit) = repository.getMaleShirts(myCallback)
}