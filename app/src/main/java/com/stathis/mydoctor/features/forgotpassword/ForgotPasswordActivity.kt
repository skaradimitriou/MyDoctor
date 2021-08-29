package com.stathis.mydoctor.features.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class ForgotPasswordActivity : AbstractActivity(R.layout.activity_forgot_password) {

    private lateinit var viewModel : ForgotPasswordViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
    }

    override fun running() {
        //
    }

    override fun stopped() {

    }

}