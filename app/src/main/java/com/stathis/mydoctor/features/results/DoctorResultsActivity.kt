package com.stathis.mydoctor.features.results

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.callbacks.DoctorClickListener
import com.stathis.mydoctor.features.doctor.DoctorActivity
import com.stathis.mydoctor.models.Doctor
import kotlinx.android.synthetic.main.activity_doctor_results.*

class DoctorResultsActivity : AbstractActivity(R.layout.activity_doctor_results) {

    private lateinit var viewModel : DoctorResultsViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(DoctorResultsViewModel::class.java)
    }

    override fun running() {
        val category = intent.getStringExtra("CATEGORY")

        when(category.isNullOrEmpty()){
            true -> {}
            false -> {
                doctor_results_txt.text = category
                viewModel.getDoctorsForCategory(category)
            }
        }

        doctor_results_recycler.adapter = viewModel.adapter

        viewModel.observe(this,object : DoctorClickListener{
            override fun onDoctorTap(doctor: Doctor) = openDoctorScreen(doctor)
        })
    }

    override fun stopped() = viewModel.release(this)

    private fun openDoctorScreen(doctor: Doctor) {
        val json = Gson().toJson(doctor)
        startActivity(Intent(this,DoctorActivity::class.java).also{
            it.putExtra("DOCTOR",json)
        })
    }
}