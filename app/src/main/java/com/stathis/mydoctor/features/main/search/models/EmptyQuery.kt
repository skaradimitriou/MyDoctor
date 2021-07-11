package com.stathis.mydoctor.features.main.search.models

import com.stathis.mydoctor.abstraction.LocalModel

data class EmptyQuery(val headerMessage : String, val description : String) : LocalModel{
    override fun equals(data: LocalModel): Boolean = false
}
