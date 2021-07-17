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
import com.stathis.mydoctor.features.main.search.models.EmptyQuery
import com.stathis.mydoctor.features.main.search.models.EmptyResult
import com.stathis.mydoctor.features.results.adapter.DoctorResultsAdapter
import com.stathis.mydoctor.models.Doctor

class DoctorResultsViewModel : ViewModel(), ItemClickListener {

    private val firestore =  FirebaseFirestore.getInstance()
    private lateinit var callback: DoctorClickListener
    val adapter = DoctorResultsAdapter(this)
    val doctors = MutableLiveData<List<Doctor>>()

    fun getDoctorsForCategory(category: String) {
        firestore.collection("doctors")
            .whereEqualTo("category", category)
            .get(Source.SERVER)
            .addOnSuccessListener { docs ->
                val doctorList = arrayListOf<Doctor>()
                for (document in docs) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val doctor = document.toObject(Doctor::class.java)
                    doctorList.add(doctor)
                }
                doctors.value = doctorList
            }.addOnFailureListener {
                Log.d("TAG", "Error getting documents: ", it)
                doctors.value = null
            }
    }

    fun observe(owner: LifecycleOwner, callback: DoctorClickListener) {
        this.callback = callback

        doctors.observe(owner, Observer {
            bindResults(it)
        })
    }

    private fun bindResults(list : List<Doctor>) {
        when(list.isNullOrEmpty()){
            true -> {
                adapter.submitList(
                    listOf(EmptyResult(
                            "No Doctors Available",
                            "We couldn't find doctor results for this category. Once we got results, they will appear here.")))
            }

            false -> adapter.submitList(list)
        }

        adapter.notifyDataSetChanged()
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