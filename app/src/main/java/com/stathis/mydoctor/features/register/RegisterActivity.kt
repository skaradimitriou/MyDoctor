package com.stathis.mydoctor.features.register

import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class RegisterActivity : AbstractActivity(R.layout.activity_register) {

    private lateinit var viewModel : RegisterViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        /*
        As a user, I want to have a clean screen with the following options:
        - Enter my Register data (email, password,pass confirm)
        - Register Button

        Also: The following functionalities need to be implemented:
        - Validate user input
        - Check if passwords match and if it is a strong password
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