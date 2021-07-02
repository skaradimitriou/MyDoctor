package com.stathis.mydoctor.features.main.overview.model

import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.Doctor

data class DoctorParent(

    val header : String,
    val list : List<Doctor>

) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
