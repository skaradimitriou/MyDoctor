package com.stathis.mydoctor.features.reschedule

import android.text.format.DateFormat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.internal.LifecycleCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.stathis.mydoctor.models.Appointment
import com.stathis.mydoctor.models.Doctor
import java.util.*

class RescheduleViewModel : ViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private var userAppointments = mutableListOf<Appointment>()
    val appointmentUpdated = MutableLiveData<Boolean>()
    val appointmentHour = MutableLiveData<String>()
    val appointmentDate = MutableLiveData<String>()
    private lateinit var doctor: Doctor
    private lateinit var oldAppointment: Appointment

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

    private fun saveAppointment(date : String, time : String, reason : String){
        val appointment = Appointment(doctor, date, time, reason)

        val newAppointmentList = mutableListOf<Appointment>()
        userAppointments.forEach {
            when(it != oldAppointment){
                true -> {
                    newAppointmentList.add(it)
                }
            }
        }

        newAppointmentList.add(0,appointment)

        val docRef = firestore.collection("appointments").document(auth.currentUser!!.uid)
        val data = hashMapOf<String, Any>(
            "appointments" to newAppointmentList
        )

        docRef.set(data).addOnSuccessListener {
            appointmentUpdated.value = true
        }
    }

    fun setSelectedDoctor(doctor: Doctor) {
        this.doctor = doctor
    }

    fun validateUserInput(date: String, time: String, reason: String) {
        when (date.isNullOrEmpty() and time.isNullOrEmpty() and reason.isNullOrEmpty()) {
            true -> {
            } //throw message
            false -> saveAppointment(date, time, reason)
        }
    }

    fun validateTime(hour: Int, minute: Int) {
        when (minute) {
            0 -> appointmentHour.value = "$hour:00"
            else -> appointmentHour.value = "$hour:$minute"
        }
    }

    fun validateDate(date: Long) {
        val fullDate: String = DateFormat.format("dd/MM/yyyy", Date(date)).toString()
        appointmentDate.value = fullDate
    }

    fun setOldAppointment(oldAppointment: String) {
        val model = Gson().fromJson(oldAppointment, Appointment::class.java)
        this.oldAppointment = model
    }

    fun release(owner: LifecycleOwner) {
        appointmentUpdated.removeObservers(owner)
        appointmentDate.removeObservers(owner)
        appointmentHour.removeObservers(owner)
    }
}