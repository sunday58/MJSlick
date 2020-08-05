package com.mjslick.ui.female

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mjslick.database.FirebaseSource
import com.mjslick.database.Repository
import com.mjslick.model.LadiesWear

class AddFemaleClothViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository
    private var firebaseSource: FirebaseSource = FirebaseSource()


    init {
        repository = Repository(firebaseSource)
    }

    fun addFemaleCloth(item: LadiesWear) = repository.addFemaleCloth(item)

    fun uploadFemaleClothImage(imageBytes: List<ByteArray>,
                               onSuccess: (imagePath: ArrayList<String>) -> Unit)
                    = repository.upLoadFemaleClothImage(imageBytes, onSuccess)
}