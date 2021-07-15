package com.stathis.mydoctor.features.main.overview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.DiffUtilClass
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.main.overview.model.AllCategoriesModel
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.PromoItem

class OverviewChildAdapter(private val callback : ItemClickListener) : ListAdapter<LocalModel, OverviewChildViewHolder>(DiffUtilClass<LocalModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType,parent,false)
        return OverviewChildViewHolder(view,callback)
    }

    override fun onBindViewHolder(holder: OverviewChildViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is Doctor -> R.layout.holder_home_doctor_card_item
            is Category -> R.layout.holder_home_category_item
            is PromoItem -> R.layout.holder_home_promo_item
            is AllCategoriesModel -> R.layout.holder_all_categories_item
            else -> R.layout.holder_empty_layout
        }
    }
}