package com.stathis.mydoctor.features.appointment_details.model

import com.stathis.mydoctor.abstraction.LocalModel

data class AppointmentDetail(

    val header : String,
    val desc : String,
    val img : Int

) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
