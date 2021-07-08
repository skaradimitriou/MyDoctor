package com.stathis.mydoctor.features.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.DiffUtilClass
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.Query

class SearchParentAdapter(private val callback : ItemClickListener) : ListAdapter<LocalModel, SearchParentViewHolder>(DiffUtilClass<LocalModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType,parent,false)
        return SearchParentViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: SearchParentViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is Doctor -> R.layout.holder_doctor_results_item
            is Query -> R.layout.holder_query_item
            else -> R.layout.holder_empty_layout
        }
    }
}