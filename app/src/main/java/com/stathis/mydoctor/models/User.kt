package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class User( val username : String = "", val userPhoto : String = "") : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
