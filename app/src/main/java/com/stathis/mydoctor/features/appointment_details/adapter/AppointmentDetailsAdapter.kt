package com.stathis.mydoctor.features.appointment_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.DiffUtilClass
import com.stathis.mydoctor.abstraction.LocalModel

class AppointmentDetailsAdapter() : ListAdapter<LocalModel, AppointmentDetailsViewHolder>(DiffUtilClass<LocalModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): AppointmentDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_appointment_card_item,parent,false)
        return AppointmentDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentDetailsViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}