package com.gwc.ui.male

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gwc.database.FirebaseSource
import com.gwc.database.Repository
import com.gwc.model.MaleWear

class AddMaleClothViewModel(application: Application): AndroidViewModel(application) {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()


    init {
        repository = Repository(firebaseSource)
    }

    fun addMaleCloth(item: MaleWear) = repository.addMaleCloth(item)

    fun uploadMaleClothImage(imageBytes: List<ByteArray>,
                               onSuccess: (imagePath: ArrayList<String>) -> Unit)
            = repository.uploadMaleClothImage(imageBytes, onSuccess)

}