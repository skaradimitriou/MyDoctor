package com.stathis.mydoctor.features.main.overview

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment
import com.stathis.mydoctor.callbacks.HomeClickListener
import com.stathis.mydoctor.features.caterories.CategoriesActivity
import com.stathis.mydoctor.features.caterories.adapter.CategoriesAdapter
import com.stathis.mydoctor.features.doctor.DoctorActivity
import com.stathis.mydoctor.features.main.overview.model.AllCategoriesModel
import com.stathis.mydoctor.features.results.DoctorResultsActivity
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.fragment_overview.*


class OverviewFragment : AbstractFragment(R.layout.fragment_overview) {

    private lateinit var viewModel : OverviewViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun running() {
        /*
        FIXME
            - fix the UI for doctors to be more good-looking
            - Add a lazy loading effect
            - Add all categories so I can click on it and open categories screen
            - Add all doctors so I can click and get all doctors (?)
         */

        home_nested_recycler.adapter = viewModel.adapter

        viewModel.bindCallbacks(object : HomeClickListener{
            override fun onCategoryTap(category: Category) = getDoctors(category)
            override fun onDoctorTap(doctor: Doctor) = openDoctorScreen(doctor)
            override fun openAllCategories(data: AllCategoriesModel) = goToCategoryScreen(data)
        })

        viewModel.observe(viewLifecycleOwner)
    }

    override fun stopped() = viewModel.release(viewLifecycleOwner)

    private fun openDoctorScreen(doctor: Doctor) {
        val model = Gson().toJson(doctor)
        startActivity(Intent(requireContext(), DoctorActivity::class.java).also{
            it.putExtra("DOCTOR",model)
        })
    }

    private fun getDoctors(category: Category) {
        startActivity(Intent(requireContext(), DoctorResultsActivity::class.java).also{
            it.putExtra("CATEGORY",category.description)
        })
    }

    private fun goToCategoryScreen(data: AllCategoriesModel) {
        startActivity(Intent(requireContext(), CategoriesActivity::class.java))
    }
}