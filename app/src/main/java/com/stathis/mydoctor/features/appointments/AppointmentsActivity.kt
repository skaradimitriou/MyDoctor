package com.stathis.mydoctor.features.appointments

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        appointment_save_btn.setOnClickListener {

        }

        viewModel.appointmentSaved.observe(this, Observer {
            when (it) {
                true -> {
                } // Show snackbar
                false -> Unit
            }
        })
    }

    private fun bindDoctorInfo(model: Doctor) {
        /*
        FIXME:

        UI screen that contains: info about the doctor at the top,
        and edit texts in the middle screen so the user can fill
        the reason of his appointment and the date
         */
    }

    private fun selectDates() {
        /*
            Logic: Bind data to the UI
            -> add a button in the bottom for him to add his appointment
         */

        val dateRangePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Ημερομηνία Ραντεβού")
            .build()

        new_appointment.setOnClickListener {
            dateRangePicker.show(this.supportFragmentManager, "SELECT DATES")
            dateRangePicker.addOnPositiveButtonClickListener {
                Log.d("", it.toString())
            }

            Log.d("", dateRangePicker.toString())
        }
    }

    override fun stopped() = viewModel.appointmentSaved.removeObservers(this)
}