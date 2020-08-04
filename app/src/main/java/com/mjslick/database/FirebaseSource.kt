package com.mjslick.database

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mjslick.model.LadiesWear
import com.mjslick.model.User
import com.mjslick.utility.Constants
import java.util.*
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
            "users/${FirebaseAuth.getInstance().currentUser?.email
            ?: throw NullPointerException("UID is null.")}")

    private val currentUserStorageRef: StorageReference
        get() = storageInstance.reference
            .child(FirebaseAuth.getInstance().currentUser?.uid ?:
            throw NullPointerException("UID is null"))


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

    fun addFemaleClothListener(context: Context,
                                onListen: (List<LadiesWear>) -> Unit): ListenerRegistration{
        return currentUserDocRef
            .collection(Constants.FEMALE_CLOTH_COLLECTION)
            .orderBy(Constants.CLOTH_TYPE)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Firestore", "female cloths listener error", error)
                    return@addSnapshotListener
                }
                val items = mutableListOf<LadiesWear>()
                value!!.documents.forEach {

                    //TODO("add item to adapter")
                }
                onListen(items)
            }
    }

    //database storage
    fun uploadFemaleClothImage(imageBytes: List<ByteArray>){

//        val urlString = ArrayList<String>()
        val ref = currentUserStorageRef.child(
            "femaleCloths")

        for (uploadCount in imageBytes.indices) {
            val individualImage = imageBytes[uploadCount]
            val imageName = ref.child("Images" + UUID.randomUUID().toString())

            imageName.putBytes(individualImage)
                .addOnSuccessListener {

                    imageName.downloadUrl.addOnSuccessListener {uri ->
//                        urlString.add(uri.toString())
                        val url = java.lang.String.valueOf(uri)
                        getImageUrlLink(url)
                    }
                }
        }
    }

    private fun getImageUrlLink(imagePath: String){

        val hashMap: HashMap<String, Any> = HashMap()

        hashMap["clothesImage"] = imagePath

//        for (i in imagePath.indices){
//            hashMap[Constants.CLOTH_IMAGES] = imagePath[i]

            currentUserDocRef
                .collection(Constants.FEMALE_CLOTH_COLLECTION)
                .add(hashMap)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        Log.d("imageUrl", "Uploaded image urls")
                    }
                }.addOnFailureListener {exception ->
                    Log.d("ImageUrlError",
                        "Failed to upload image Url", exception)
                }

    }

    fun pathToReference(path: String) = storageInstance.getReference(path)
    }
