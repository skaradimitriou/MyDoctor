package com.stathis.mydoctor.features.doctor


import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class DoctorActivity : AbstractActivity(R.layout.activity_doctor) {

    private lateinit var viewModel : DoctorViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
    }

    override fun running() {
        //
    }

    override fun stopped() {
        //
    }
}