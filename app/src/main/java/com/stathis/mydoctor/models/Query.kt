package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class Query(val queryName : String) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
