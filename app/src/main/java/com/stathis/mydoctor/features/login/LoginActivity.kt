package com.stathis.mydoctor.features.login

import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class LoginActivity : AbstractActivity(R.layout.activity_login) {

    private lateinit var viewModel : LoginViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        /*
        As a user, I want to have a clean screen with the following options:
        - Enter my login data (email, password)
        - Login Button

        Also: The following functionalities need to be implemented:
        - Validate user input
        - Authenticate user to the app

         */
    }

    override fun running() {
        //
    }

    override fun stopped() {
        //
    }
}