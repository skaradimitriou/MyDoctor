package com.stathis.mydoctor.features.appointment_details


import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.reschedule.RescheduleActivity
import com.stathis.mydoctor.models.Appointment
import kotlinx.android.synthetic.main.activity_appointment_details.*

class AppointmentDetailsActivity : AbstractActivity(R.layout.activity_appointment_details) {

    private lateinit var viewModel: AppointmentDetailsViewModel
    private lateinit var appointment: Appointment

    override fun init() {
        viewModel = ViewModelProvider(this).get(AppointmentDetailsViewModel::class.java)
    }

    override fun running() {
        appointment_details.adapter = viewModel.adapter

        val model = intent.getStringExtra("APPOINTMENT")

        model?.let {
            appointment = Gson().fromJson(model, Appointment::class.java)
            bindAppointmentData(appointment)
        }

        viewModel.observe(this)

        reschedule_btn.setOnClickListener {
            startActivity(Intent(this, RescheduleActivity::class.java).also{
                it.putExtra("OLD_APPOINTMENT",model)
            })
        }

        cancel_btn.setOnClickListener {
            showDialog()
        }

        viewModel.appointmentCancelled.observe(this, Observer {
            when (it) {
                true -> {
                    onBackPressed()
                    finish()
                }
                else -> Unit
            }
        })
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this).also {
            it.setTitle(resources.getString(R.string.popup_title))
            it.setMessage(resources.getString(R.string.popup_desc))
            it.setPositiveButton(resources.getString(R.string.popup_positive_btn)) { dialog, which -> viewModel.cancelAppointment(appointment) }
            it.setNegativeButton(resources.getString(R.string.popup_negative_btn)) { dialog, which -> dialog.dismiss() }
        }
        val alert = builder.create()
        alert.show()
    }

    private fun bindAppointmentData(data: Appointment) {
        Glide.with(this).load(data.doctor.image).into(appointment_img)
        doctor_name.text = data.doctor.fullname

        viewModel.bindAppointmentDetails(data)
    }

    override fun stopped() = viewModel.release(this)
}