package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

class HeaderModel(val user: User) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}