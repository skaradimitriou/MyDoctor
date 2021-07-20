package com.stathis.mydoctor.features.appointments

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.models.Doctor
import kotlinx.android.synthetic.main.activity_appointments.*

class AppointmentsActivity : AbstractActivity(R.layout.activity_appointments) {

    private lateinit var viewModel: AppointmentScreenViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(AppointmentScreenViewModel::class.java)
    }

    override fun running() {
        val model = intent.getStringExtra("DOCTOR")

        model?.let {
            val doctor = Gson().fromJson(it, Doctor::class.java)
            bindDoctorInfo(doctor)
        }

        appointment_save_btn.setOnClickListener{
            val appointmentReason = visit_reason.text.toString()
            val appointmentDate = appointment_date.text.toString()

            viewModel.validateUserInput()
        }

        viewModel.appointmentSaved.observe(this, Observer {
            when (it) {
                true -> {} // Show snackbar
                false -> Unit
            }
        })
    }

    private fun bindDoctorInfo(model: Doctor) {
        Glide.with(this).load(model.image).into(doctor_img)
        doctor_name.text = model.fullname
        doctor_category.text = model.category
        appointment_location.text = resources.getString(R.string.dummy_doc_hospital)

        selectDates()
    }

    private fun selectDates() {
        /*
            Logic: Bind data to the UI
            -> add a button in the bottom for him to add his appointment
         */

        val dateRangePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Ημερομηνία Ραντεβού")
            .build()

        appointment_date.setOnClickListener {
            dateRangePicker.show(this.supportFragmentManager, "SELECT DATES")
            dateRangePicker.addOnPositiveButtonClickListener {
                val date = dateRangePicker.headerText
                appointment_date.text = date
            }

            Log.d("", dateRangePicker.toString())
        }
    }

    override fun stopped() = viewModel.appointmentSaved.removeObservers(this)
}