package com.stathis.mydoctor.features.main.appointments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.DiffUtilClass
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel

class AppointmentsAdapter(private val callback : ItemClickListener) : ListAdapter<LocalModel, AppointmentsViewHolder>(DiffUtilClass<LocalModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_appointment_item,parent,false)
        return AppointmentsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: AppointmentsViewHolder, position: Int) = holder.bindData(getItem(position))
}