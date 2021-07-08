package com.stathis.mydoctor.callbacks

import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.Query

interface SearchClickListener {

    fun onDoctorTap(doc : Doctor)
    fun onCategoryTap(category : Category)
    fun onQueryTap(query : Query)
}