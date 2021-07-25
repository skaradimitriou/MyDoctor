package com.stathis.mydoctor.features.reschedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.stathis.mydoctor.models.Appointment

class RescheduleViewModel : ViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private var userAppointments = mutableListOf<Appointment>()
    val appointmentUpdated = MutableLiveData<Boolean>()

    init {
        getUserAppointments()
    }

    private fun getUserAppointments() {
        firestore.collection("appointments")
            .document(auth.currentUser!!.uid).addSnapshotListener { p0, p1 ->
                p0?.data?.toList()?.forEach {
                    val json = Gson().toJsonTree(it.second)
                    userAppointments = Gson().fromJson(json, Array<Appointment>::class.java).toMutableList()
                }
            }
    }

    fun updateAppointment(appointment: Appointment){
        when(userAppointments.contains(appointment)){
            true ->  {
                //FIXME: update the appointment with the given hours
            }
            false -> Unit
        }
    }

    private fun saveToDatabase(){
        val docRef = firestore.collection("appointments").document(auth.currentUser!!.uid)

        val data = hashMapOf<String, Any>(
            "appointments" to userAppointments
        )

        docRef.set(data).addOnSuccessListener {
            appointmentUpdated.value = true
        }
    }
}