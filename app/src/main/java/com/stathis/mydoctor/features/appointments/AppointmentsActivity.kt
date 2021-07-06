package com.stathis.mydoctor.features.appointments

import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class AppointmentsActivity : AbstractActivity(R.layout.activity_appointments) {

    private lateinit var viewModel : AppointmentScreenViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(AppointmentScreenViewModel::class.java)
    }

    override fun running() {

    }

    override fun stopped() {}
}