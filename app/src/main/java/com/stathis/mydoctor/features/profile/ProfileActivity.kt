package com.stathis.mydoctor.features.profile

import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class ProfileActivity : AbstractActivity(R.layout.activity_profile) {

    private lateinit var viewModel : ProfileViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        /*
        As a user, I want to:
            - view my profile
            - be able to upload a photo to my profile
            - be able to view my personal data
            - be able to change my personal data if I want to
         */
    }

    override fun running() {

    }

    override fun stopped() {

    }
}