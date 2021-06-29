package com.stathis.mydoctor.features.caterories

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.callbacks.CategoryCallback
import com.stathis.mydoctor.features.caterories.model.DoctorCategory
import com.stathis.mydoctor.features.results.DoctorResultsActivity
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AbstractActivity(R.layout.activity_categories) {

    private lateinit var viewModel : CategoriesViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
    }

    override fun running() {
        viewModel.bindCallback(object : CategoryCallback{
            override fun onCategoryTap(category: DoctorCategory) = getDoctorsForCategory(category)
        })

        categories_recycler.adapter = viewModel.adapter
        viewModel.observe(this)
    }

    override fun stopped() = viewModel.release(this)

    private fun getDoctorsForCategory(category: DoctorCategory) {
        startActivity(Intent(this, DoctorResultsActivity::class.java).also{
            it.putExtra("CATEGORY",category.category_name)
        })
    }
}