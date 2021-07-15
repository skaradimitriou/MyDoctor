package com.stathis.mydoctor.features.appointment_details.adapter

import android.view.View
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.appointment_details.model.AppointmentDetail
import kotlinx.android.synthetic.main.holder_appointment_card_item.view.*

class AppointmentDetailsViewHolder(itemView: View) : AbstractViewHolder(itemView) {

    override fun present(data: LocalModel) {
        when(data){
            is AppointmentDetail -> {
                itemView.item_header.text = data.header
                itemView.item_desc.text = data.desc
                itemView.item_img.setImageResource(data.img)
            }
        }
    }
}