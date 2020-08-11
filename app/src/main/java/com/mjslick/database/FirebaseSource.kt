package com.mjslick.database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mjslick.model.LadiesWear
import com.mjslick.model.MaleWear
import com.mjslick.model.User
import com.mjslick.utility.Constants
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val storageInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance() }


    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document(
            "users/MJSlick")

    private val currentUserStorageRef: StorageReference
        get() = storageInstance.reference
            .child("MJSlick")


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


        val newUser: Task<Void> =
           currentUserDocRef
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

     fun addFemaleCloth( item: LadiesWear){

        val femaleCloth: MutableMap<String, Any> = HashMap()
        femaleCloth[Constants.CLOTH_NAME] = item.clothName
        femaleCloth[Constants.CLOTH_TYPE] = item.clothType
        femaleCloth[Constants.CLOTH_DESCRIPTION] = item.clothDescription
        femaleCloth[Constants.TOP_PRICE] = item.topPrice
        femaleCloth[Constants.TROUSER_PRICE] = item.trouserPrice
        femaleCloth[Constants.COMPLETE_PRICE] = item.completePrice
         femaleCloth[Constants.CLOTH_IMAGES] = item.clothImages


        val newFemaleCloth: Task<DocumentReference> =
            currentUserDocRef
                .collection(Constants.FEMALE_CLOTH_COLLECTION)
                .add(femaleCloth)

               newFemaleCloth.addOnSuccessListener {
                    Log.d("female Cloth", "Cloth added")
                }.addOnFailureListener {
                    Log.d("female cloth", "failed to add female cloth")
                }

    }

    fun addMaleCloth( item: MaleWear){

        val maleCloth: MutableMap<String, Any> = HashMap()
        maleCloth[Constants.CLOTH_NAME] = item.clothName
        maleCloth[Constants.CLOTH_TYPE] = item.clothType
        maleCloth[Constants.CLOTH_DESCRIPTION] = item.clothDescription
        maleCloth[Constants.TOP_PRICE] = item.topPrice
        maleCloth[Constants.TROUSER_PRICE] = item.trouserPrice
        maleCloth[Constants.COMPLETE_PRICE] = item.completePrice
        maleCloth[Constants.CLOTH_IMAGES] = item.clothImages


        val newMaleCloth: Task<DocumentReference> =
            currentUserDocRef
                .collection(Constants.MALE_CLOTH_COLLECTION)
                .add(maleCloth)

        newMaleCloth.addOnSuccessListener {
            Log.d("male Cloth", "Cloth added")
        }.addOnFailureListener {
            Log.d("male cloth", "failed to add male cloth")
        }

    }

    fun getFemaleCloths(myCallback: (List<LadiesWear>) -> Unit) {
        currentUserDocRef
            .collection(Constants.FEMALE_CLOTH_COLLECTION)
            .orderBy(Constants.CLOTH_TYPE)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val list: ArrayList<LadiesWear> = ArrayList()
                    for (document in task.result!!) {
                        val wears = document.toObject(LadiesWear::class.java)
                        list.add(wears)

                    }
                    myCallback(list)
                }

            }
    }

    fun getFemaleSearchCloths(name: String, myCallback: (List<LadiesWear>) -> Unit){
        currentUserDocRef
            .collection(Constants.FEMALE_CLOTH_COLLECTION)
            .orderBy(Constants.CLOTH_NAME)
            .startAt(name)
            .endAt(name + "\uf8ff")
            .get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    val list: ArrayList<LadiesWear> = ArrayList()
                    for (document in task.result!!){
                        val wears = document.toObject(LadiesWear::class.java)
                        list.add(wears)
                    }
                    myCallback(list)
                }
            }
    }

    fun getMaleCloths(myCallback: (List<MaleWear>) -> Unit){
        currentUserDocRef
            .collection(Constants.MALE_CLOTH_COLLECTION)
            .orderBy(Constants.CLOTH_TYPE)
            .get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    val list: ArrayList<MaleWear> = ArrayList()
                    for (document in task.result!!){
                        val wears = document.toObject(MaleWear::class.java)
                        list.add(wears)
                    }
                    myCallback(list)
                }
            }
    }

    fun getMaleTrousers(myCallback: (List<MaleWear>) -> Unit){
        currentUserDocRef
            .collection(Constants.MALE_CLOTH_COLLECTION)
            .whereEqualTo(Constants.CLOTH_TYPE, "Trouser")
            .get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    val list: ArrayList<MaleWear> = ArrayList()
                    for (document in task.result!!){
                        val wears = document.toObject(MaleWear::class.java)
                        list.add(wears)
                    }
                    myCallback(list)
                }
            }
    }

    fun getMaleShirts(myCallback: (List<MaleWear>) -> Unit){
        currentUserDocRef
            .collection(Constants.MALE_CLOTH_COLLECTION)
            .whereEqualTo(Constants.CLOTH_TYPE, "Shirt")
            .get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    val list: ArrayList<MaleWear> = ArrayList()
                    for (document in task.result!!){
                        val wears = document.toObject(MaleWear::class.java)
                        list.add(wears)
                    }
                    myCallback(list)
                }
            }
    }

    fun getMaleSearchCloths(name: String, myCallback: (List<MaleWear>) -> Unit){
        currentUserDocRef
            .collection(Constants.MALE_CLOTH_COLLECTION)
            .orderBy(Constants.CLOTH_NAME)
            .startAt(name)
            .endAt(name + "\uf8ff")
            .get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    val list: ArrayList<MaleWear> = ArrayList()
                    for (document in task.result!!){
                        val wears = document.toObject(MaleWear::class.java)
                        list.add(wears)
                    }
                    myCallback(list)
                }
            }
    }

    //database storage
    fun uploadFemaleClothImage(imageBytes: List<ByteArray>,
                                onSuccess: (imagePath: ArrayList<String>) -> Unit){

        val ref = currentUserStorageRef.child(
            "femaleCloths")

        for (uploadCount in imageBytes.indices) {
            val individualImage = imageBytes[uploadCount]
            val imageName = ref.child("Images" + UUID.randomUUID().toString())

            imageName.putBytes(individualImage)
                .addOnSuccessListener {
                    imageName.downloadUrl.addOnSuccessListener {uri ->
                        val url = java.lang.String.valueOf(uri)
                        onSuccess(arrayListOf(url))
                    }
                }
        }
    }

    fun uploadMaleClothImage(imageBytes: List<ByteArray>,
                               onSuccess: (imagePath: ArrayList<String>) -> Unit){

        val ref = currentUserStorageRef.child(
            "maleCloths")

        for (uploadCount in imageBytes.indices) {
            val individualImage = imageBytes[uploadCount]
            val imageName = ref.child("Images" + UUID.randomUUID().toString())

            imageName.putBytes(individualImage)
                .addOnSuccessListener {
                    imageName.downloadUrl.addOnSuccessListener {uri ->
                        val url = java.lang.String.valueOf(uri)
                        onSuccess(arrayListOf(url))
                    }
                }
        }
    }

    fun pathToReference(path: String) = storageInstance.getReference(path)
    }
