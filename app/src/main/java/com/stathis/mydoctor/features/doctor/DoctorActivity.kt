package com.stathis.mydoctor.features.doctor


import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.appointments.AppointmentsActivity
import com.stathis.mydoctor.models.Doctor
import kotlinx.android.synthetic.main.activity_doctor.*

class DoctorActivity : AbstractActivity(R.layout.activity_doctor) {

    private lateinit var viewModel: DoctorViewModel
    private lateinit var doctor: Doctor
    private var favorite = false

    override fun init() {
        viewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
    }

    override fun running() {
        val doctorData = intent.getStringExtra("DOCTOR")

        doctorData?.let {
            doctor = Gson().fromJson(doctorData, Doctor::class.java)

            bindDoctorData(doctor)
            viewModel.checkIfFavorite(doctor)
        }

        favorite_icon.setOnClickListener {
            when(favorite){
                true -> viewModel.removeFromFavorites(doctor)
                false -> viewModel.addToFavorites(doctor)
            }
        }

        book_appointment.setOnClickListener {
            goToAppointments()
        }

        viewModel.isFavorite.observe(this, Observer{
            when(it){
                true -> {
                    favorite = true
                    favorite_icon.frame = 20
                }
                false -> {
                    favorite = false
                    favorite_icon.frame = 0
                }
            }
        })
    }

    private fun play() {
        favorite_icon.playAnimation()
    }

    private fun goToAppointments() {
        val model = Gson().toJson(doctor)

        startActivity(Intent(this, AppointmentsActivity::class.java).also {
            it.putExtra("DOCTOR", model)
        })
    }

    override fun stopped() {}

    private fun bindDoctorData(doctor: Doctor) {
        Glide.with(this).load(doctor.image).into(doctor_img)
        doctor_name.text = "Dr. ${doctor.fullname}"
        doctor_category.text = doctor.category
        reviews_counter.text = doctor.rating.toString()
        experience_counter.text = "${doctor.experience} Years"
    }
}