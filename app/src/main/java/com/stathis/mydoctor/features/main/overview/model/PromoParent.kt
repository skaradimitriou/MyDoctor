package com.stathis.mydoctor.features.main.overview.model

import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.PromoItem

data class PromoParent(val list : List<PromoItem>) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
