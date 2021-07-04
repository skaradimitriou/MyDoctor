package com.stathis.mydoctor.callbacks

import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor

interface HomeClickListener {
    fun onCategoryTap(category : Category)
    fun onDoctorTap(doctor : Doctor)
}