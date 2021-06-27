package com.stathis.mydoctor.features.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    val userRegisted = MutableLiveData<Boolean>()

    fun validateData(email: String, pass: String) {
        when(checkPasswordStrength(pass)){
            true -> {
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                    when(it.isSuccessful){
                        true -> userRegisted.value = true
                        false -> userRegisted.value = false
                    }
                }
            }
            false -> {} // throw some kind of error to notify the user
        }
    }

    private fun checkPasswordStrength(pass : String) : Boolean {
        return true
    }
}
