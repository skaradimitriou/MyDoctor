package com.stathis.mydoctor.features.results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.DiffUtilClass
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel

class DoctorResultsAdapter(private val callback : ItemClickListener) : ListAdapter<LocalModel, DoctorResultsViewHolder>(DiffUtilClass<LocalModel>()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorResultsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_doctor_results_item,parent,false)
        return DoctorResultsViewHolder(view,callback)
    }

    override fun onBindViewHolder(holder: DoctorResultsViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}
