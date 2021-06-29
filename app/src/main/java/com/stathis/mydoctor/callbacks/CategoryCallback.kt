package com.stathis.mydoctor.callbacks

import com.stathis.mydoctor.features.caterories.model.DoctorCategory

interface CategoryCallback {
    fun onCategoryTap(category : DoctorCategory)
}