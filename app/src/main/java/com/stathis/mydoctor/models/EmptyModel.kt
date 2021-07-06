package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class EmptyModel(val message : String) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}