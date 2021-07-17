package com.stathis.mydoctor.features.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.mydoctor.utils.PasswordValidator

class RegisterViewModel : ViewModel() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }
    val userRegisted = MutableLiveData<Boolean>()

    fun validateData(email: String, pass: String) {
        when (PasswordValidator.validatePassword(pass)) {
            true -> {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    when (it.isSuccessful) {
                        true -> {
                            saveUserData(email)
                            userRegisted.value = true
                        }
                        false -> userRegisted.value = false
                    }
                }
            }
            false -> {
            } // throw some kind of error to notify the user
        }
    }

    fun saveUserData(email: String) {
        val documentReference = firestore.collection("users").document(auth.currentUser!!.uid)

        val data: HashMap<String, Any> = hashMapOf(
            "username" to email.takeWhile { it != '@' },
            "location" to "Not available yet",
            "userImg" to "https://nd.net/wp-content/uploads/2016/04/profile-dummy.png",
            "email" to email,
            "telephone" to "Not available yet"
        )

        documentReference.set(data).addOnSuccessListener {
            Log.d("", "User profile is created for Stathis")
            userRegisted.value = true
        }

        documentReference.set(data).addOnFailureListener {
            Log.d("", "User profile is created for Stathis")
            userRegisted.value = false
        }
    }
}
