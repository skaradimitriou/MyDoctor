package com.stathis.mydoctor.features.appointments

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.main.MainActivity
import com.stathis.mydoctor.models.Doctor
import kotlinx.android.synthetic.main.activity_appointments.*
import java.util.*

class AppointmentsActivity : AbstractActivity(R.layout.activity_appointments) {

    private lateinit var viewModel: AppointmentScreenViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(AppointmentScreenViewModel::class.java)
    }

    override fun running() {
        val model = intent.getStringExtra("DOCTOR")

        model?.let {
            val doctor = Gson().fromJson(it, Doctor::class.java)

            viewModel.setSelectedDoctor(doctor)
            bindDoctorInfo(doctor)
        }

        appointment_save_btn.setOnClickListener{
            val reason = visit_reason.text.toString()
            val date = appointment_date.text.toString()
            val time = appointment_time.text.toString()

            viewModel.validateUserInput(date,time,reason)
        }

        observeData()
    }

    private fun observeData() {
        viewModel.appointmentSaved.observe(this, Observer {
            when (it) {
                true -> notifyAndExit()
                false -> Unit
            }
        })

        viewModel.appointmentHour.observe(this,Observer{
            appointment_time.text = it
        })

        viewModel.appointmentDate.observe(this, Observer {
            appointment_date.text = it
        })
    }

    private fun notifyAndExit() {
        Toast.makeText(this,"Appointment Saved",Toast.LENGTH_LONG).show()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, 1500)
    }

    private fun bindDoctorInfo(model: Doctor) {
        Glide.with(this).load(model.image).into(doctor_img)
        doctor_name.text = model.fullname
        doctor_category.text = model.category
        appointment_location.text = resources.getString(R.string.dummy_doc_hospital)

        selectDates()
        selectTime()
    }

    private fun selectTime() {
        val picker = SnapTimePickerDialog.Builder()
            .setTitle(R.string.time_header)
            .setPrefix(R.string.time_suffix)
            .setSuffix(R.string.time_prefix)
            .setThemeColor(R.color.ocean_blue)
            .setTitleColor(R.color.white).build()

        appointment_time.setOnClickListener {
            picker.show(this.supportFragmentManager, "SELECT TIME")
            picker.setListener { pickerHour, pickerMin ->
                val hour = pickerHour
                val minute = pickerMin

                viewModel.validateTime(hour, minute)
            }
        }
    }

    private fun selectDates() {
        val dateRangePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Ημερομηνία Ραντεβού")
            .build()

        appointment_date.setOnClickListener {
            dateRangePicker.show(this.supportFragmentManager, "SELECT DATES")
            dateRangePicker.addOnPositiveButtonClickListener {
                viewModel.validateDate(it)
            }
        }
    }

    override fun stopped() = viewModel.appointmentSaved.removeObservers(this)
}