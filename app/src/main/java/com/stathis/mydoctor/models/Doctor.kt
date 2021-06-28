package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class Doctor(

    val fullname : String,
    val image : String,
    val rating : Double,
    val telephone : String,
    val category : String,
    val experience : Int

) : LocalModel {
    constructor() : this("", "", 0.0,"","", 0)
    override fun equals(data: LocalModel): Boolean = false
}
