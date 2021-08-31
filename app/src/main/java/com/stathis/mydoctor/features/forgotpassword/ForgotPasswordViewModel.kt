package com.stathis.mydoctor.features.forgotpassword

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.stathis.mydoctor.abstraction.AbstractAndroidViewModel

class ForgotPasswordViewModel(app : Application) : AbstractAndroidViewModel(app) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    val userVerified = MutableLiveData<Boolean>()

    fun verifyEmail(email : String) {
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val isNewUser: Boolean? = it.result?.signInMethods?.isEmpty()
                    if (isNewUser!!) {
                        Log.e("TAG", "Is New User!")
                        userVerified.value = false
                    } else {
                        sendEmail(email)
                    }
                }
            }
    }

    private fun sendEmail(email : String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userVerified.value = true
                }
            }
    }
}