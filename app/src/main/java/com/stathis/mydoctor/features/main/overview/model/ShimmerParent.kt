package com.stathis.mydoctor.features.main.overview.model

import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.ShimmerObject

class ShimmerParent(val data : List<ShimmerObject>) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}