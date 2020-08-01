package com.mjslick.database

import com.mjslick.model.User

class Repository(private val firebaseSource: FirebaseSource) {

    fun login(email: String, password: String) = firebaseSource.signin(email, password)

    fun register(email: String, password: String, user: User) =
        firebaseSource.register(email, password, user)

    fun currentUser() = firebaseSource.currentUser()

    fun logout() = firebaseSource.logout()

    fun message(toastMessage: String) = firebaseSource.message(toastMessage)
}