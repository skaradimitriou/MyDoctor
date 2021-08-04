package com.stathis.mydoctor.features.editprofile

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.models.User
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.profile_user_img
import kotlinx.android.synthetic.main.activity_edit_profile.profile_user_name
import kotlinx.android.synthetic.main.activity_profile.*

class EditProfileActivity : AbstractActivity(R.layout.activity_edit_profile) {

    private lateinit var viewModel: EditProfileViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
    }

    override fun running() {
        profile_user_img.setOnClickListener{
            //open dialog with options
        }

        edit_profile_save_btn.setOnClickListener{
            //save user data to firestore
        }

        observeData()
    }

    private fun observeData() {
        viewModel.userData.observe(this, Observer {
            it?.let { binduserData(it) }
        })
    }

    override fun stopped() {}

    private fun binduserData(user: User) {
        when (user.userPhoto.isNullOrEmpty()) {
            true -> profile_user_img.setImageResource(R.drawable.empty_profile_photo)
            false -> Glide.with(this).load(user.userPhoto).into(profile_user_img)
        }

        profile_user_name.text = user.username
        viewModel.bindProfileDetails(user)
    }
}