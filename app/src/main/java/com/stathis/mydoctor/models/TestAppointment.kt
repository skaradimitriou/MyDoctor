package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class TestAppointment(

    val appointment : Appointment

) : LocalModel {
    constructor() : this(Appointment(Doctor("", "", 0.0, "", "", 0),"","",""))
    override fun equals(data: LocalModel): Boolean = false
}