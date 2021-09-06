package com.stathis.mydoctor.features.editprofile

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractAndroidViewModel
import com.stathis.mydoctor.features.editprofile.adapter.EditProfileAdapter
import com.stathis.mydoctor.features.profile.adapter.ProfileAdapter
import com.stathis.mydoctor.features.profile.model.ProfileItem
import com.stathis.mydoctor.models.User
import com.stathis.mydoctor.utils.TAG
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.ByteArrayOutputStream

class EditProfileViewModel(app : Application) : AbstractAndroidViewModel(app) {

    val auth by lazy { FirebaseAuth.getInstance() }
    val firestore by lazy { FirebaseFirestore.getInstance() }
    val userData = MutableLiveData<User>()
    val adapter = EditProfileAdapter()
    val userImageUrl = MutableLiveData<String>()
    var editableMode = false

    init {
        getUserData()
    }

    fun getUserData() {
        firestore.collection("users").document(auth.currentUser!!.uid).get()
            .addOnCompleteListener { task ->
                when (task.isSuccessful) {
                    true -> {
                        val result = task.result

                        Log.d(TAG, "${result?.id} => ${result?.data}")
                        val user = result!!.toObject(User::class.java)!!

                        userData.value = user
                    }
                    false -> userData.value = null
                }
            }
    }

    fun bindProfileDetails(user: User) {
        val list = listOf(
            ProfileItem(getString(R.string.profile_screen_email), user.email),
            ProfileItem(getString(R.string.profile_screen_telephone), user.telephone),
            ProfileItem(getString(R.string.profile_screen_location), user.location)
        )

        adapter.submitList(list)
    }

    fun saveGalleryPhotoToDb(uri: Uri) {
        val storageRef = FirebaseStorage.getInstance()
            .reference.child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
        val upload = storageRef.putFile(uri)
        upload.addOnSuccessListener {
            val result = it.metadata!!.reference!!.downloadUrl
            result.addOnSuccessListener {
                val imageLink = it.toString()
                Log.d("MSG", imageLink)
                savePhotoToDb(imageLink)
            }
        }
    }

    fun saveCameraPhotoToDb(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference.child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()
        val upload = storageRef.putBytes(image)
        upload.addOnSuccessListener {
            val result = it.metadata!!.reference!!.downloadUrl
            result.addOnSuccessListener {
                val imageLink = it.toString()
                Log.d("MSG", imageLink)
                savePhotoToDb(imageLink)
            }
        }
    }

    fun savePhotoToDb(string: String) {
        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .update(mapOf("userPhoto" to string))
        getUserPhoto()
    }

    fun getUserPhoto() {
        firestore.collection("users").document(auth.currentUser!!.uid).get()
            .addOnCompleteListener { task ->
                when (task.isSuccessful) {
                    true -> {
                        val result = task.result
                        Log.d(TAG, "${result?.id} => ${result?.data}")
                        val user = result!!.toObject(User::class.java)!!

                        userImageUrl.value = user.userPhoto
                    }
                    false -> {
                        // handle this
                        Log.d(TAG, task.toString())
                    }
                }
            }
    }

    fun release(owner : LifecycleOwner) {
        userData.removeObservers(owner)
        userImageUrl.removeObservers(owner)
    }

    fun setEditMode() {
        editableMode = !editableMode
        adapter.changeEditMode(editableMode)
    }

    fun saveDataToFirestore() {}
}