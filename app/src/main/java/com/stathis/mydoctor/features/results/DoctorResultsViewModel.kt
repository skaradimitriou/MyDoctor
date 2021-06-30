package com.stathis.mydoctor.features.results

import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.callbacks.DoctorClickListener
import com.stathis.mydoctor.features.results.adapter.DoctorResultsAdapter
import com.stathis.mydoctor.models.Doctor

class DoctorResultsViewModel : ViewModel(), ItemClickListener {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var callback: DoctorClickListener
    val adapter = DoctorResultsAdapter(this)
    val doctors = MutableLiveData<List<Doctor>>()

    init {
        getDummyDoctors()
    }

    private fun getDummyDoctors() {
        firestore.collection("doctors").get().addOnSuccessListener { docs ->
            val doctorList = arrayListOf<Doctor>()
            for (document in docs) {
                Log.d("TAG", "${document.id} => ${document.data}")
                val doctor = document.toObject(Doctor::class.java)
                doctorList.add(doctor)
            }

            doctors.value = doctorList
        }.addOnFailureListener {
            Log.d("TAG", "Error getting documents: ", it)
        }
    }

    fun observe(owner: LifecycleOwner, callback: DoctorClickListener) {
        this.callback = callback

        doctors.observe(owner, Observer {
            adapter.submitList(it)
        })
    }

    fun release(owner: LifecycleOwner) {
        doctors.removeObservers(owner)
    }

    override fun onItemTap(view: View) {
        when (view.tag) {
            is Doctor -> callback.onDoctorTap(view.tag as Doctor)
        }
    }
}