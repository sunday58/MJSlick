package com.mjslick.ui.male

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository
import com.mjslick.model.MaleWear

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