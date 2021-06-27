package com.stathis.mydoctor.features.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.stathis.mydoctor.PasswordValidator

class LoginViewModel : ViewModel() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    val userAuthenticated = MutableLiveData<Boolean>()

    fun authenticateUser(email : String, pass : String) {
        when(PasswordValidator.validatePassword(pass)){
            true -> {
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    when(it.isSuccessful){
                        true -> userAuthenticated.value = true
                        false -> userAuthenticated.value = false
                    }
                }
            }
            false -> {}
        }
    }
}
