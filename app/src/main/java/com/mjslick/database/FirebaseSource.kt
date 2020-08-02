package com.mjslick.database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import com.mjslick.model.User
import com.mjslick.utility.Constants
import java.util.*


class FirebaseSource {


    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun register(email: String, password: String, user: User){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful) {
                val isNewUser = task.result?.additionalUserInfo?.isNewUser
                    if (isNewUser!!){
                        initCurrentUserFirstTime(user)
                    }else {
                        task.exception
                    }

                }else {
                    task.exception
                }
            }
    }

    fun signin(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    task.isComplete
                }else if (!task.isSuccessful) {
                   FirebaseAuth.AuthStateListener {
                       try {
                           throw task.exception!!.fillInStackTrace()
                       }
                       catch (invalidEmail: FirebaseAuthInvalidUserException){
                           Log.d("error", invalidEmail.message!!)
                       }
                       catch (wrongCredentials: FirebaseAuthInvalidCredentialsException){
                           Log.d("error", wrongCredentials.message!!)
                       }
                       catch (e: Exception){
                           Log.d("error", e.message!!)
                       }
                   }
                }
            }
    }

  private  fun initCurrentUserFirstTime(firebaseUser: User) {

        val user: MutableMap<String, Any> = HashMap()
        user[Constants.EMAIL] = firebaseUser.email
        user[Constants.PHONE] = firebaseUser.phone
        user[Constants.TWITTER] = firebaseUser.twitter
        user[Constants.FACEBOOK] = firebaseUser.facebook
        user[Constants.LINKEDIN] = firebaseUser.linkedin
        user[Constants.LADIESWEAR] = firebaseUser.ladiesWear
        user[Constants.MALEWEAR] = firebaseUser.maleWear

        val newUser: Task<Void> =
            firestoreInstance.collection(Constants.USERS_COLLECTION).document(firebaseUser.email)
                .set(user)
        newUser.addOnSuccessListener {
            Log.d("New User", "User was successfully added")

        }.addOnFailureListener { e ->
            Log.d("New User", "Error has occurred " + e.message)
        }

    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

     fun resetPassword(email: String) {
         firebaseAuth.sendPasswordResetEmail(email)
             .addOnCompleteListener {task ->
                 if (task.isSuccessful){
                     Log.d("onComplete", "password reset email sent")
                 }else {
                     Log.d("onComplete", "Link not sent")
                 }
             }
     }

    }
