package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class Appointment(

    val doctor: Doctor,
    val date: String,
    val hour: String,
    val reason : String


) : LocalModel {
    constructor() : this(
        Doctor("", "", 0.0, "", "", 0),
        "", "","")

    override fun equals(data: LocalModel): Boolean = false
}
