package com.stathis.mydoctor.features.intro

import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class IntroActivity : AbstractActivity(R.layout.activity_intro) {

    private lateinit var viewModel : IntroViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(IntroViewModel::class.java)

        /*
        As a user, I want to have a clean screen with the following options:

        - Login Button
        - Register Button

        Also: The following functionalities need to be implemented:
        - Check if user is logged in, then redirect him to main screen

         */
    }

    override fun running() {
        //
    }

    override fun stopped() {
        //
    }
}