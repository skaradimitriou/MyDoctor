package com.stathis.mydoctor.features.appointment_details


import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class AppointmentDetailsActivity : AbstractActivity(R.layout.activity_appointment_details) {

    private lateinit var viewModel : AppointmentDetailsViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(AppointmentDetailsViewModel::class.java)
    }

    override fun running() {
        /*
        Logic bellow:

        - View my appointment details in a clean way
        - Be able to cancel my appointment or call my doctor

         */
    }

    override fun stopped() = viewModel.release(this)

}