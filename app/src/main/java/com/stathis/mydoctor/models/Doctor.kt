package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class Doctor(

    val name : String,
    val category : String,
    val telephone : String,
    val email : String

) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
