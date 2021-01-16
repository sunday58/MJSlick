package com.gwc.ui.male

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository
import com.gwc.model.MaleWear

class MaleViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()

    init {
        repository = Repository(firebaseSource)
    }

    fun getMaleCloths(myCallback: (List<MaleWear>) -> Unit) = repository.getMaleCloths(myCallback)

    fun currentUser() = repository.currentUser()
}