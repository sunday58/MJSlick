package com.mjslick.database

import com.mjslick.model.LadiesWear
import com.mjslick.model.MaleWear
import com.mjslick.model.User

class Repository(private val firebaseSource: FirebaseSource) {

    fun login(email: String, password: String) = firebaseSource.signin(email, password)

    fun register(email: String, password: String, user: User) =
        firebaseSource.register(email, password, user)

    fun currentUser() = firebaseSource.currentUser()

    fun logout() = firebaseSource.logout()

    fun resetPassword(email: String) = firebaseSource.resetPassword(email)

    fun addFemaleCloth(item: LadiesWear) = firebaseSource.addFemaleCloth(item)

    fun upLoadFemaleClothImage(imageBytes: List<ByteArray>,
                               onSuccess: (imagePath: ArrayList<String>) -> Unit)
            = firebaseSource.uploadFemaleClothImage(imageBytes, onSuccess)

    fun addMaleCloth(item: MaleWear) = firebaseSource.addMaleCloth(item)

    fun uploadMaleClothImage(imageBytes: List<ByteArray>,
                             onSuccess: (imagePath: ArrayList<String>) -> Unit)
                = firebaseSource.uploadMaleClothImage(imageBytes, onSuccess)
}