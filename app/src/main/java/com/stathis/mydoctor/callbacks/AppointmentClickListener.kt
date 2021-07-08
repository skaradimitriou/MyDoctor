package com.stathis.mydoctor.callbacks

import com.stathis.mydoctor.models.Appointment

interface AppointmentClickListener {
    fun onAppointmentTap(event : Appointment)
}