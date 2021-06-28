package com.stathis.mydoctor.features.main.overview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.stathis.mydoctor.models.Doctor

class OverviewViewModel : ViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    val doctorList = MutableLiveData<List<Doctor>>()

    init{
        getDoctorsByCategory("Ορθοπεδικός")
    }

    fun getDoctors() {
        firestore.collection("doctors").get(Source.SERVER).addOnSuccessListener { docs ->
            val doctors = arrayListOf<Doctor>()
            for (document in docs) {
                Log.d("TAG", "${document.id} => ${document.data}")
                val doctor = document.toObject(Doctor::class.java)
                doctors.add(doctor)
            }

            doctorList.value = doctors
        }.addOnFailureListener {
            Log.d("TAG", "Error getting documents: ", it)
        }
    }

    fun getDoctorsByCategory(category : String){
        firestore.collection("doctors").whereEqualTo("category",category).get(Source.SERVER).addOnSuccessListener { docs ->
            val doctors = arrayListOf<Doctor>()
            for (document in docs) {
                Log.d("TAG", "${document.id} => ${document.data}")
                val doctor = document.toObject(Doctor::class.java)
                doctors.add(doctor)
            }

            doctorList.value = doctors
        }.addOnFailureListener {
            Log.d("TAG", "Error getting documents: ", it)
        }
    }
}