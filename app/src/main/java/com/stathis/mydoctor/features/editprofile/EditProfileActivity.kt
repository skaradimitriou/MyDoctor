package com.stathis.mydoctor.features.editprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.models.User
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.profile_user_img
import kotlinx.android.synthetic.main.activity_edit_profile.profile_user_name
import kotlinx.android.synthetic.main.bottom_sheet_dialog_upload_options.view.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class EditProfileActivity : AbstractActivity(R.layout.activity_edit_profile), EasyPermissions.PermissionCallbacks {

    private lateinit var viewModel: EditProfileViewModel
    private val REQUEST_IMAGE_CAPTURE = 100
    private val IMAGE_PICK_CODE = 200

    override fun init() {
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
    }

    override fun running() {
        /*
            FIXME: Users should be able to:
                   1. enter their data in the input texts
                   2. save their new data from the input texts
         */

        askForPermissions()

        profile_details_recycler.adapter = viewModel.adapter

        val test = ""

        viewModel.setEditMode()

        profile_user_img.setOnClickListener{
            //open dialog with options
            when(hasPermissions()){
                true -> showUploadOptions()
                false -> askForPermissions()
            }
        }

        edit_profile_save_btn.setOnClickListener{
            //save user data to firestore

            viewModel.setEditMode()

            /*
            FIXME: Add the new input to firestore
             */
            viewModel.saveDataToFirestore()

        }

        observeData()
    }

    private fun showUploadOptions() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_upload_options, null)
        val dialog = BottomSheetDialog(this,R.style.BottomSheetDialogTheme).also{
            it.setContentView(view)
            it.show()
        }
        view.closeDialog.setOnClickListener{ dialog.dismiss() }

        view.uploadFromCamera.setOnClickListener{
            uploadFromCamera()
            dialog.dismiss()
        }

        view.uploadFromGallery.setOnClickListener{
            uploadFromGallery()
            dialog.dismiss()
        }
    }

    private fun uploadFromCamera(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            pictureIntent.resolveActivity(this.packageManager!!)?.also {
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun uploadFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun hasPermissions() = EasyPermissions.hasPermissions(
        this, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun askForPermissions() {
        EasyPermissions.requestPermissions(this,"This app cant work without gallery permissions",REQUEST_IMAGE_CAPTURE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this,"This app cant work without gallery permissions",IMAGE_PICK_CODE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun observeData() {
        viewModel.userData.observe(this, Observer {
            it?.let { binduserData(it) }
        })

        viewModel.userImageUrl.observe(this,Observer{
            it?.let { Glide.with(this).load(it).into(profile_user_img) }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    override fun stopped() = viewModel.release(this)

    private fun binduserData(user: User) {
        when (user.userPhoto.isNullOrEmpty()) {
            true -> profile_user_img.setImageResource(R.drawable.empty_profile_photo)
            false -> Glide.with(this).load(user.userPhoto).into(profile_user_img)
        }

        profile_user_name.text = user.username
        viewModel.bindProfileDetails(user)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(this).build().show()
        } else {
            askForPermissions()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            viewModel.saveCameraPhotoToDb(imgBitmap)

        } else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // I have to save the url to the db
            val imageUri = data?.data
            if (imageUri != null) {
                viewModel.saveGalleryPhotoToDb(imageUri)
                viewModel.getUserPhoto()
            }
        }
    }
}