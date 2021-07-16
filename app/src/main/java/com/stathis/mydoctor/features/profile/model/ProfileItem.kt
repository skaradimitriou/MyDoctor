package com.stathis.mydoctor.features.profile.model

import com.stathis.mydoctor.abstraction.LocalModel

data class ProfileItem(

    val header : String,
    val value : String

) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
