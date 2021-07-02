package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class Category (

    val description : String,
    val image : Int

) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
