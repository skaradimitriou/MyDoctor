package com.stathis.mydoctor.callbacks

import com.stathis.mydoctor.models.Doctor

interface DoctorClickListener {
    fun onDoctorTap(doctor : Doctor)
}