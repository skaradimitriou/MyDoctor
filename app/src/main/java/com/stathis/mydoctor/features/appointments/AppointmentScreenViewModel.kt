package com.stathis.mydoctor.features.appointments

import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.Appointment
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.Query
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class AppointmentScreenViewModel : ViewModel() {

    val firestore by lazy { FirebaseFirestore.getInstance() }
    val auth by lazy { FirebaseAuth.getInstance() }
    private var userAppointments = mutableListOf<LocalModel>()
    val appointmentSaved = MutableLiveData<Boolean>()
    val appointmentHour = MutableLiveData<String>()
    val appointmentDate = MutableLiveData<String>()
    private lateinit var doctor: Doctor

    init{
        getUserAppointments()
    }

    fun setSelectedDoctor(doctor: Doctor) {
        this.doctor = doctor
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

    private fun saveAppointment(date: String, time: String, reason: String) {
        val appointment = Appointment(doctor, date, time, reason)
        userAppointments.add(0, appointment)

        val docRef = firestore.collection("appointments").document(auth.currentUser!!.uid)

        val data = hashMapOf<String, Any>(
            "appointments" to userAppointments
        )

        docRef.set(data).addOnSuccessListener {
            appointmentSaved.value = true
        }
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
}
