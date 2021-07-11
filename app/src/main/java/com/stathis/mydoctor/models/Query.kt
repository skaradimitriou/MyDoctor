package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class Query(val query : String) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
