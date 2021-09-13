package com.stathis.mydoctor.features.results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.DiffUtilClass
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.main.search.models.EmptyResult
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.ShimmerObject

class DoctorResultsAdapter(private val callback : ItemClickListener) : ListAdapter<LocalModel, DoctorResultsViewHolder>(DiffUtilClass<LocalModel>()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorResultsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent,false)
        return DoctorResultsViewHolder(view,callback)
    }

    override fun onBindViewHolder(holder: DoctorResultsViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)){
            is ShimmerObject -> R.layout.holder_shimmer_doctor_results_item
            is Doctor -> R.layout.holder_doctor_results_item
            is EmptyResult -> R.layout.holder_no_results_found_item
            else -> R.layout.holder_empty_layout
    }
}
