package com.stathis.mydoctor.features.profile

import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class ProfileActivity : AbstractActivity(R.layout.activity_profile) {

    private lateinit var viewModel : ProfileViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        /*
        As a user, I want to have a clean screen with the following options:
        - Enter my login data (email, password)
        - Login Button

        Also: The following functionalities need to be implemented:
        - Validate user input
        - Check if passwords match and if it is a strong password
        - Authenticate user to the app

         */
    }

    override fun running() {

    }

    override fun stopped() {

    }
}