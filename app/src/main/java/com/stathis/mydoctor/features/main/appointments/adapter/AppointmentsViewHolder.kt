package com.stathis.mydoctor.features.main.appointments.adapter

import android.view.View
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.Appointment
import kotlinx.android.synthetic.main.holder_appointment_item.view.*

class AppointmentsViewHolder(itemView : View,callback : ItemClickListener) : AbstractViewHolder(itemView,callback) {

    override fun present(data: LocalModel) {
        when(data){
            is Appointment -> {
                itemView.appointment_name.text = data.description
            }
        }
    }
}