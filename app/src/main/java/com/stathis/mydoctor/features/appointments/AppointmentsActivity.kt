package com.stathis.mydoctor.features.appointments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class AppointmentsActivity : AbstractActivity(R.layout.activity_appointments) {

    private lateinit var viewModel : AppointmentScreenViewModel

    override fun init() {
        /*
        Logic of this screen:

        - get the data of the appointment that the user has selected from the appointment fragment
        - ask the user if he is sure and wants to save that appointment
        - save the appointment data that the user has selected to the firestore database
        - go back (?)

         */

        viewModel = ViewModelProvider(this).get(AppointmentScreenViewModel::class.java)
    }

    override fun running() {
        verifyAppointmentInfo()

        viewModel.appointmentSaved.observe(this, Observer{
            when(it){
                true -> {} // Show snackbar
                false -> Unit
            }
        })
    }

    private fun verifyAppointmentInfo() {
        /*
            Logic: Bind data to the UI
            -> add a button in the bottom for him to add his appointment
         */
    }

    override fun stopped() = viewModel.appointmentSaved.removeObservers(this)
}