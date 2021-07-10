package com.stathis.mydoctor.features.main.appointments.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.google.type.DateTime
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.Appointment
import com.stathis.mydoctor.models.EmptyModel
import kotlinx.android.synthetic.main.holder_appointment_item.view.*
import kotlinx.android.synthetic.main.holder_empty_appointment.view.*
import java.util.*


class AppointmentsViewHolder(itemView : View,callback : ItemClickListener) : AbstractViewHolder(itemView,callback) {

    override fun present(data: LocalModel) {
        when(data){
            is Appointment -> {
                itemView.appointment_name.text = data.doctorName
                itemView.appointment_desc.text = data.description
                itemView.appointment_location.text = data.location

                itemView.appointment_time.text = "${data.date} | ${data.startHour} - ${data.endHour} (1h)"

                Glide.with(itemView).load(data.doctorImg).into(itemView.appointment_doctor_img)
            }

            is EmptyModel -> itemView.holder_empty_appointments.text = data.message
        }
    }
}