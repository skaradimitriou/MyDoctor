package com.stathis.mydoctor.features.main.overview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.DiffUtilClass
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.main.overview.model.*
import com.stathis.mydoctor.models.HeaderModel
import com.stathis.mydoctor.models.ShimmerObject

class OverviewScreenAdapter(private val callback : ItemClickListener) : ListAdapter<LocalModel, OverviewScreenViewHolder>(DiffUtilClass<LocalModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewScreenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType,parent,false)
        return OverviewScreenViewHolder(view,callback)
    }

    override fun onBindViewHolder(holder: OverviewScreenViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)){
        is ShimmerHeader -> R.layout.holder_home_shimmer_header_item
        is ShimmerPromo -> R.layout.holder_nested_parent_viewpager
        is ShimmerParent -> R.layout.holder_shimmer_home_parent_item
        is ShimmerCategoryParent -> R.layout.holder_nested_parent_item
        is HeaderModel -> R.layout.holder_home_header_item
        is CategoryParent -> R.layout.holder_nested_parent_item
        is DoctorParent -> R.layout.holder_nested_parent_item
        is PromoParent -> R.layout.holder_nested_parent_viewpager
        else -> R.layout.holder_empty_layout
    }
}