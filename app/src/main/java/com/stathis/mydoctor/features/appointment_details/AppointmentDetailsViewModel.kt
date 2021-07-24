package com.stathis.mydoctor.features.appointment_details

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.appointment_details.adapter.AppointmentDetailsAdapter
import com.stathis.mydoctor.features.appointment_details.model.AppointmentDetail
import com.stathis.mydoctor.models.Appointment

class AppointmentDetailsViewModel : ViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private var userAppointments = mutableListOf<Appointment>()
    val adapter = AppointmentDetailsAdapter()
    val details = MutableLiveData<List<LocalModel>>()
    val appointmentCancelled = MutableLiveData<Boolean>()

    init{
        getUserAppointments()
    }

    fun bindAppointmentDetails(data: Appointment) {
        val list = listOf(
            AppointmentDetail("Date & Time","${data.date} | ${data.hour}", R.drawable.ic_monthly_calendar),
            AppointmentDetail("Location","Athens",R.drawable.ic_location),
            AppointmentDetail("Type of visit",data.reason,R.drawable.ic_visit_reason),
            AppointmentDetail("Estimated Cost","25$",R.drawable.ic_euro))

        details.value = list
    }

    fun observe(owner: LifecycleOwner) {
        details.observe(owner, Observer{
            adapter.submitList(it)
        })
    }

    fun release(owner: LifecycleOwner) = details.removeObservers(owner)

    private fun getUserAppointments() {
        firestore.collection("appointments")
            .document(auth.currentUser!!.uid).addSnapshotListener { p0, p1 ->
                p0?.data?.toList()?.forEach {
                    val json = Gson().toJsonTree(it.second)
                    userAppointments = Gson().fromJson(json, Array<Appointment>::class.java).toMutableList()
                }
            }
    }

    fun cancelAppointment(appointment: Appointment) {
        when(userAppointments.contains(appointment)){
            true -> {
                userAppointments.remove(appointment)
                updateUserAppointments()
            }
            else -> Unit
        }
    }

    private fun updateUserAppointments() {
        val docRef = firestore.collection("appointments").document(auth.currentUser!!.uid)

        val data = hashMapOf<String, Any>(
            "appointments" to userAppointments
        )

        docRef.set(data).addOnSuccessListener {
            appointmentCancelled.value = true
        }
    }
}
