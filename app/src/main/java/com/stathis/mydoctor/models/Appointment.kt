package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class Appointment(

    val patientName : String,
    val doctorName : String,
    val location : String,
    val description : String

) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
