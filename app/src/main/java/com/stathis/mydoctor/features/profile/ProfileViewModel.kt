package com.stathis.mydoctor.features.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.mydoctor.features.profile.adapter.ProfileAdapter
import com.stathis.mydoctor.features.profile.model.ProfileItem
import com.stathis.mydoctor.models.User
import com.stathis.mydoctor.utils.TAG

class ProfileViewModel : ViewModel() {

    val auth by lazy { FirebaseAuth.getInstance() }
    val firestore by lazy { FirebaseFirestore.getInstance() }
    val userLoggedOut = MutableLiveData<Boolean>()
    val userData = MutableLiveData<User>()
    val adapter = ProfileAdapter()

    init {
        getUserData()
    }

    fun logout() {
        auth.signOut()
        userLoggedOut.value = true
    }

    fun getUserData() {
        firestore.collection("users").document(auth.currentUser!!.uid).get()
            .addOnCompleteListener { task ->
                when (task.isSuccessful) {
                    true -> {
                        val result = task.result

                        Log.d(TAG, "${result?.id} => ${result?.data}")
                        val user = result!!.toObject(User::class.java)!!

                        userData.value = user
                    }
                    false -> userData.value = null
                }
            }
    }

    fun bindProfileDetails(user: User) {
        val list = listOf(
            ProfileItem("Email", user.email),
            ProfileItem("Telephone", user.telephone)
        )

        adapter.submitList(list)
    }
}
