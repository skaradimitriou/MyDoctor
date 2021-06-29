package com.stathis.mydoctor.features.caterories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.DiffUtilClass
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel

class CategoriesAdapter(private val callback : ItemClickListener) : ListAdapter<LocalModel,CategoriesViewHolder>(DiffUtilClass<LocalModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_category_item,parent,false)
        return CategoriesViewHolder(view,callback)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}