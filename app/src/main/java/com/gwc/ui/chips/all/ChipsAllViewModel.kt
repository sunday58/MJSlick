package com.gwc.ui.chips.all

import androidx.lifecycle.ViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository
import com.gwc.model.MaleWear

class ChipsAllViewModel: ViewModel() {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun getMaleCloths(myCallback: (List<MaleWear>) -> Unit) = repository.getMaleCloths(myCallback)

}