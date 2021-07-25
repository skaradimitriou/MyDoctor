package com.stathis.mydoctor.features.reschedule

import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class RescheduleActivity : AbstractActivity(R.layout.activity_reschedule) {

    private lateinit var viewModel : RescheduleViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(RescheduleViewModel::class.java)
    }

    override fun running() {
        /*
        FIXME: As a user I want to:
               1. Be able to reschedule my appointment (date & time)

        Note: This activity should pop upwards when open
         */
    }

    override fun stopped() {}
}