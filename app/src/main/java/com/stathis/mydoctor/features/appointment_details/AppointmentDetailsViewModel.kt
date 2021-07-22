package com.stathis.mydoctor.features.appointment_details

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.appointment_details.adapter.AppointmentDetailsAdapter
import com.stathis.mydoctor.features.appointment_details.model.AppointmentDetail
import com.stathis.mydoctor.models.Appointment

class AppointmentDetailsViewModel : ViewModel() {

    val adapter = AppointmentDetailsAdapter()
    val details = MutableLiveData<List<LocalModel>>()

    fun bindAppointmentDetails(data: Appointment) {
        val list = listOf(
            AppointmentDetail("Date & Time","${data.date} | ${data.hour}", R.drawable.ic_monthly_calendar),
            AppointmentDetail("Location","Athens",R.drawable.ic_location),
            AppointmentDetail("Type of visit",data.reason,R.drawable.ic_visit_reason),
            AppointmentDetail("Estimated Cost","25$",R.drawable.ic_euro))

        details.value = list
    }

    fun observe(owner: LifecycleOwner) {
        details.observe(owner, Observer{
            adapter.submitList(it)
        })
    }

    fun release(owner: LifecycleOwner) = details.removeObservers(owner)

    fun cancelAppointment(appointment: Appointment) {

    }
}
