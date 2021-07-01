package com.stathis.mydoctor.features.doctor


import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.models.Doctor
import kotlinx.android.synthetic.main.activity_doctor.*

class DoctorActivity : AbstractActivity(R.layout.activity_doctor) {

    private lateinit var viewModel : DoctorViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
    }

    override fun running() {
        val doctorData = intent.getStringExtra("DOCTOR")

        doctorData?.let{
            val doctor = Gson().fromJson(doctorData,Doctor::class.java)
            bindDoctorData(doctor)
        }
    }

    override fun stopped() {}

    private fun bindDoctorData(doctor : Doctor){
        Glide.with(this).load(doctor.image).into(doctor_img)
        doctor_name.text = "Dr. ${doctor.fullname}"
        doctor_category.text = doctor.category
        reviews_counter.text = doctor.rating.toString()
        experience_counter.text = "${doctor.experience} Years"
    }
}