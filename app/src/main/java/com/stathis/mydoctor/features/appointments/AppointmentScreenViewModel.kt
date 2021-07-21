package com.stathis.mydoctor.features.appointments

import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.mydoctor.models.Appointment
import com.stathis.mydoctor.models.Doctor
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class AppointmentScreenViewModel : ViewModel() {

    val firestore by lazy { FirebaseFirestore.getInstance() }
    val auth by lazy { FirebaseAuth.getInstance() }
    val appointmentSaved = MutableLiveData<Boolean>()
    val appointmentHour = MutableLiveData<String>()
    val appointmentDate = MutableLiveData<String>()
    private lateinit var doctor : Doctor

    fun setSelectedDoctor(doctor: Doctor){
        this.doctor = doctor
    }

    private fun saveAppointment(date : String, time : String, reason : String){
        val appointment = Appointment(doctor,date,time,reason)

        val docRef = firestore.collection("appointments").document(auth.currentUser!!.uid)

        val data: HashMap<String, Any> = hashMapOf(
            "appointment" to appointment
        )

        docRef.set(data).addOnSuccessListener {
            Log.d("", "User profile is created for Stathis")
            appointmentSaved.value = true
        }
    }

    fun validateUserInput(date : String, time : String, reason : String) {
        when(date.isNullOrEmpty() and time.isNullOrEmpty() and reason.isNullOrEmpty()){
            true -> {} //throw message
            false -> saveAppointment(date,time,reason)
        }
    }

    fun validateTime(hour: Int, minute: Int) {
        when(minute){
            0 -> appointmentHour.value = "$hour:00"
            else -> appointmentHour.value = "$hour:$minute"
        }
    }

    fun validateDate(date : Long){
        val fullDate: String = DateFormat.format("dd/MM/yyyy", Date(date)).toString()
        appointmentDate.value = fullDate
    }
}
