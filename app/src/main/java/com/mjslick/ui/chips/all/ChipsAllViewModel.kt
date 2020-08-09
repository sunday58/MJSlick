package com.mjslick.ui.chips.all

import androidx.lifecycle.ViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository
import com.mjslick.model.MaleWear

class ChipsAllViewModel: ViewModel() {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun getMaleCloths(myCallback: (List<MaleWear>) -> Unit) = repository.getMaleCloths(myCallback)
}