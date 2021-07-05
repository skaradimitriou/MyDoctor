package com.stathis.mydoctor.features.main.appointments

import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment


class AppointmentsFragment : AbstractFragment(R.layout.fragment_appointments) {

    override fun init() {
        /*
        This screen needs to have a list of my appointments

        Logic:

            - If I have no appointments, then I need a proper indicator on screen
            - Fab button to add a new appointment
            - Calendar indicator on top of the month (?) or week (?)
         */
    }

    override fun running() {
        //
    }

    override fun stopped() {
        //
    }
}