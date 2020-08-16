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

    fun getFemaleCloths(myCallback: (List<LadiesWear>) -> Unit) = firebaseSource.getFemaleCloths(myCallback)
    fun getMaleCloths(myCallback: (List<MaleWear>) -> Unit) = firebaseSource.getMaleCloths(myCallback)
    fun getMaleTrousers(myCallback: (List<MaleWear>) -> Unit) = firebaseSource.getMaleTrousers(myCallback)
    fun getMaleShirts(myCallback: (List<MaleWear>) -> Unit) = firebaseSource.getMaleShirts(myCallback)

    fun getFemaleSearchCloths(name: String, myCallback: (List<LadiesWear>) -> Unit) =
        firebaseSource.getFemaleSearchCloths(name, myCallback)

    fun getMaleSearchCloths(name: String, myCallback: (List<MaleWear>) -> Unit) =
        firebaseSource.getMaleSearchCloths(name, myCallback)

    fun deleteMaleCloth(key: String) =
        firebaseSource.deleteMaleCloth(key)

    fun deleteFemaleCloth(key: String) =
        firebaseSource.deleteFemaleCloth(key)

}