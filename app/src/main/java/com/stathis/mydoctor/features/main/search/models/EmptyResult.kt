package com.stathis.mydoctor.features.main.search.models

import com.stathis.mydoctor.abstraction.LocalModel

data class EmptyResult(val headerMessage : String, val description : String) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}