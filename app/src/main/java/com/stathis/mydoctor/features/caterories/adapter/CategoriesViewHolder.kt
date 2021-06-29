package com.stathis.mydoctor.features.caterories.adapter

import android.view.View
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.caterories.model.DoctorCategory
import kotlinx.android.synthetic.main.holder_category_item.view.*

class CategoriesViewHolder(itemView : View,callback : ItemClickListener) : AbstractViewHolder(itemView,callback) {

    override fun present(data: LocalModel) {
        when(data){
            is DoctorCategory -> {
                itemView.category_name.text = data.category_name
            }
        }
    }

}