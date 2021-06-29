package com.stathis.mydoctor.features.caterories.model

import com.stathis.mydoctor.abstraction.LocalModel

class DoctorCategory(

    val category_name : String

) : LocalModel {

    constructor() : this("")
    override fun equals(data: LocalModel): Boolean = false
}