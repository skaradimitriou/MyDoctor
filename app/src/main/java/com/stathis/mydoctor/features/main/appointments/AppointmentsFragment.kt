package com.stathis.mydoctor.features.main.appointments

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment
import com.stathis.mydoctor.features.appointments.AppointmentsActivity
import kotlinx.android.synthetic.main.fragment_appointments.*


class AppointmentsFragment : AbstractFragment(R.layout.fragment_appointments) {

    private lateinit var viewModel : AppointmentsViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(AppointmentsViewModel::class.java)

        /*
        This screen needs to have a list of my appointments

        Logic:
            - If I have no appointments, then I need a proper indicator on screen
            - Fab button to add a new appointment
            - Calendar indicator on top of the month (?) or week (?)
         */
    }

    override fun running() {
        appointments_recycler.adapter = viewModel.adapter

        val dateRangePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Ημερομηνία Ραντεβού")
            .build()

        new_appointment.setOnClickListener{
            dateRangePicker.show(requireActivity().supportFragmentManager,"SELECT DATES")
            dateRangePicker.addOnPositiveButtonClickListener {
                    Log.d("",it.toString())
            }

            Log.d("",dateRangePicker.toString())
           //startActivity(Intent(requireContext(),AppointmentsActivity::class.java))
        }

        viewModel.observe(this)
    }

    override fun stopped() = viewModel.release(this)
}