package com.stathis.mydoctor.features.results

import android.content.Intent
import androidx.lifecycle.Observer
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
    private lateinit var category : String

    override fun init() {
        viewModel = ViewModelProvider(this).get(DoctorResultsViewModel::class.java)
    }

    override fun running() {
        category = intent.getStringExtra("CATEGORY") ?: ""

        when(category.isNullOrEmpty()){
            true -> {}
            false -> {
                doctor_results_txt.text = "Αποτελέσματα για: $category"
                viewModel.getDoctorsForCategory(category)
            }
        }

        doctor_results_recycler.adapter = viewModel.adapter

        viewModel.doctors.observe(this, Observer {
            when(it.size){
                0 -> doctor_results_txt.text = "Δεν βρέθηκαν αποτελέσματα για $category"
                else -> doctor_results_txt.text = "Βρέθηκαν ${it.size} αποτελέσματα για $category"
            }
        })

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