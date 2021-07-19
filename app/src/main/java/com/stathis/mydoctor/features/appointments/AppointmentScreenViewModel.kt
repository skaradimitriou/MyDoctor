package com.stathis.mydoctor.features.appointments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentScreenViewModel : ViewModel() {

    val firestore by lazy { FirebaseFirestore.getInstance() }
    val appointmentSaved = MutableLiveData<Boolean>()

    fun saveAppointment(){
        appointmentSaved.value = true
    }


}
