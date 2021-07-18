package com.stathis.mydoctor.features.doctor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.Query

class DoctorViewModel : ViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    val isFavorite = MutableLiveData<Boolean>()
    var favoriteList = mutableListOf<Doctor>()

    fun checkIfFavorite(doctor: Doctor) {
        firestore.collection("favorites")
            .document(auth.currentUser!!.uid).get().addOnCompleteListener { task ->
                when (task.isSuccessful) {
                    true -> {
                        val result = task.result

                        result?.data?.toList()?.forEach {
                            val json = Gson().toJsonTree(it.second)
                            Log.d("", json.toString())
                            favoriteList =
                                Gson().fromJson(json, Array<Doctor>::class.java).toMutableList()
                        }

                        when (favoriteList.contains(doctor)) {
                            true -> isFavorite.value = true
                            false -> isFavorite.value = false
                        }
                    }
                }
            }
    }

    fun addToFavorites(doctor: Doctor) {
        when (favoriteList.contains(doctor)) {
            true -> Unit // doc exists in favorites
            false -> {
                favoriteList.add(0, doctor)

                val docRef = firestore.collection("favorites").document(auth.currentUser!!.uid)
                val data = hashMapOf("favoriteList" to favoriteList)

                docRef.set(data).addOnSuccessListener {
                    checkIfFavorite(doctor)
                }
            }
        }
    }

    fun removeFromFavorites(doctor: Doctor) {
        favoriteList.remove(doctor)

        val docRef = firestore.collection("favorites").document(auth.currentUser!!.uid)
        val data = hashMapOf("favoriteList" to favoriteList)

        docRef.set(data).addOnSuccessListener {
            checkIfFavorite(doctor)
        }
    }
}
