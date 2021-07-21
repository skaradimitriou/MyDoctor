package com.stathis.mydoctor.features.appointment_details


import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.models.Appointment
import kotlinx.android.synthetic.main.activity_appointment_details.*

class AppointmentDetailsActivity : AbstractActivity(R.layout.activity_appointment_details) {

    private lateinit var viewModel : AppointmentDetailsViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(AppointmentDetailsViewModel::class.java)
    }

    override fun running() {
        appointment_details.adapter = viewModel.adapter

        val appointment = intent.getStringExtra("APPOINTMENT")

        appointment?.let{
            val model = Gson().fromJson(appointment, Appointment::class.java)
            bindAppointmentData(model)
        }

        viewModel.observe(this)

        reschedule_btn.setOnClickListener {
            //FIXME: Add logic
        }

        cancel_btn.setOnClickListener {
            //FIXME: Add logic
        }

        video_call.setOnClickListener {
            //FIXME: Add logic
        }
    }

    private fun bindAppointmentData(data : Appointment) {
        Glide.with(this).load(data.doctor.image).into(appointment_img)
        doctor_name.text = data.doctor.fullname

        viewModel.bindAppointmentDetails(data)
    }

    override fun stopped() = viewModel.release(this)
}