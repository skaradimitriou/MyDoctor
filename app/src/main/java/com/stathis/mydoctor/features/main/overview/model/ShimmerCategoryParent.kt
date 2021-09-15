package com.stathis.mydoctor.features.main.overview.model

import com.stathis.mydoctor.abstraction.LocalModel

class ShimmerCategoryParent(val list : List<ShimmerCategory>) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}