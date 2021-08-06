package com.stathis.mydoctor.features.editprofile

import android.Manifest
import android.widget.Toast
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
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class EditProfileActivity : AbstractActivity(R.layout.activity_edit_profile), EasyPermissions.PermissionCallbacks {

    private lateinit var viewModel: EditProfileViewModel
    private val REQUEST_CODE = 1

    override fun init() {
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
    }

    override fun running() {
        askForPermissions()

        profile_user_img.setOnClickListener{
            //open dialog with options
            when(hasPermissions()){
                true -> uploadPhoto()
                false -> askForPermissions()
            }
        }

        edit_profile_save_btn.setOnClickListener{
            //save user data to firestore
        }

        observeData()
    }

    private fun uploadPhoto() {
        /*
        FIXME: open dialog fragment to choose gallery or camera
                upload photo to firebase storage
                then save the photo to firebase firestore
         */

    }

    private fun hasPermissions() = EasyPermissions.hasPermissions(
        this, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun askForPermissions() {
        EasyPermissions.requestPermissions(this,
            "This app cant work without gallery permissions",REQUEST_CODE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun observeData() {
        viewModel.userData.observe(this, Observer {
            it?.let { binduserData(it) }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
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

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this,"Permissions Granted",Toast.LENGTH_LONG).show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(this).build().show()
        } else {
            askForPermissions()
        }
    }
}