package com.stathis.mydoctor.callbacks

import com.stathis.mydoctor.features.main.overview.model.AllCategoriesModel
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor

interface HomeClickListener {
    fun onProfileTap()
    fun onCategoryTap(category : Category)
    fun onDoctorTap(doctor : Doctor)
    fun openAllCategories(data : AllCategoriesModel)
}