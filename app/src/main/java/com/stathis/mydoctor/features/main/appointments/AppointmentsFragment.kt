package com.stathis.mydoctor.features.main.appointments

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment
import com.stathis.mydoctor.callbacks.AppointmentClickListener
import com.stathis.mydoctor.features.appointment_details.AppointmentDetailsActivity
import com.stathis.mydoctor.features.appointments.AppointmentsActivity
import com.stathis.mydoctor.models.Appointment
import kotlinx.android.synthetic.main.fragment_appointments.*


class AppointmentsFragment : AbstractFragment(R.layout.fragment_appointments) {

    private lateinit var viewModel : AppointmentsViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(AppointmentsViewModel::class.java)
    }

    override fun running() {
        /*
        FIXME: 1. Change the UI of the empty appointment list
         */

        appointments_recycler.adapter = viewModel.adapter

        viewModel.bindCallbacks(object : AppointmentClickListener {
            override fun onAppointmentTap(event: Appointment) {
                val model = Gson().toJson(event)
                startActivity(Intent(requireContext(), AppointmentDetailsActivity::class.java).also{
                    it.putExtra("APPOINTMENT",model)
                })
            }
        })

        viewModel.observe(this)
    }

    override fun stopped() = viewModel.release(this)
}