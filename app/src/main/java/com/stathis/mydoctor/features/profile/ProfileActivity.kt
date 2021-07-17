package com.stathis.mydoctor.features.profile

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.login.LoginActivity
import com.stathis.mydoctor.models.User
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AbstractActivity(R.layout.activity_profile) {

    private lateinit var viewModel: ProfileViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun running() {
        profile_logout_btn.setOnClickListener {
            viewModel.logout()
        }

        observe()

        profile_details_recycler.adapter = viewModel.adapter
    }

    private fun observe() {
        viewModel.userLoggedOut.observe(this, Observer {
            when (it) {
                true -> goToLogin()
                false -> Unit
            }
        })

        viewModel.userData.observe(this, Observer {
            it?.let { binduserData(it) }
        })
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun binduserData(user: User) {
        when(user.userPhoto.isNullOrEmpty()){
            true -> profile_user_img.setImageResource(R.drawable.empty_profile_photo)
            false -> Glide.with(this).load(user.userPhoto).into(profile_user_img)
        }

        profile_user_name.text = user.username
        viewModel.bindProfileDetails(user)
    }

    override fun stopped() {}
}