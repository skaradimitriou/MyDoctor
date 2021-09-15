package com.stathis.mydoctor.features.main.overview.model

import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.ShimmerObject

class ShimmerPromo(val data : List<ShimmerPromoItem>) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}