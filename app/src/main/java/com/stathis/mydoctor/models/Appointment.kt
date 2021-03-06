package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class Appointment(

    val doctor: Doctor = Doctor("", "", 0.0, "", "", 0),
    val date: String = "",
    val hour: String = "",
    val reason : String = ""


) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
