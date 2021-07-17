package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class User(
    val username: String = "",
    val userPhoto: String = "",
    val email: String = "",
    val telephone: String = "",
    val location: String = ""
) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
