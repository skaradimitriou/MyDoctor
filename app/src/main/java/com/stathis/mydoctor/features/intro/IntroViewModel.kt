package com.stathis.mydoctor.features.intro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class IntroViewModel : ViewModel() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    val userIsLoggedIn = MutableLiveData<Boolean>()

    init {
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        val user = auth.currentUser
        user?.let { userIsLoggedIn.value = true }
    }
}
