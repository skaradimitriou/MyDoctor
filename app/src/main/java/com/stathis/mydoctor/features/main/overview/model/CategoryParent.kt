package com.stathis.mydoctor.features.main.overview.model

import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.Category

data class CategoryParent(

    val header : String,
    val list : List<Category>

) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
