package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class Appointment(

    val doctorName : String,
    val doctorImg : String,
    val description : String,
    val location : String,
    val patientName : String,
    val date : String,
    val startHour : String,
    val endHour : String


) : LocalModel {
    constructor() : this("","", "", "Athens","","", "","")
    override fun equals(data: LocalModel): Boolean = false
}
