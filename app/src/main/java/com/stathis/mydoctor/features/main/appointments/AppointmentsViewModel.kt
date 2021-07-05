package com.stathis.mydoctor.features.main.appointments

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.main.appointments.adapter.AppointmentsAdapter
import com.stathis.mydoctor.models.Appointment

class AppointmentsViewModel : ViewModel(), ItemClickListener {

    private val list = MutableLiveData<List<LocalModel>>()
    val adapter = AppointmentsAdapter(this)

    init {
        createDummyList()
    }

    private fun createDummyList() {
        val appointmentList = listOf(
            Appointment("","","","My 1st Appointment"),
            Appointment("","","","My 2nd Appointment"),
            Appointment("","","","My 3rd Appointment"),
            Appointment("","","","My 4th Appointment"),
            Appointment("","","","My 5th Appointment"),
            Appointment("","","","My 6th Appointment"))

        list.value = appointmentList
    }

    fun observe(owner : LifecycleOwner){
        list.observe(owner, Observer{
            it?.let { adapter.submitList(it) }
        })
    }

    fun release(owner: LifecycleOwner) = list.removeObservers(owner)
    override fun onItemTap(view: View) {
        when(view.tag){
            is Appointment -> {}
        }
    }
}
