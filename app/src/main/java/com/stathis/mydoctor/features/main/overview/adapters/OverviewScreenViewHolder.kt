package com.stathis.mydoctor.features.main.overview.adapters

import android.view.View
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.main.overview.model.CategoryParent
import com.stathis.mydoctor.features.main.overview.model.DoctorParent
import com.stathis.mydoctor.features.main.overview.model.PromoParent
import kotlinx.android.synthetic.main.holder_nested_parent_item.view.*
import kotlinx.android.synthetic.main.holder_nested_parent_viewpager.view.*

class OverviewScreenViewHolder(itemView : View,callback : ItemClickListener) : AbstractViewHolder(itemView,callback){

    private val callback = callback

    override fun present(data: LocalModel) {
        when(data){
            is CategoryParent -> {
                itemView.nested_header.text = data.header
                val adapter = OverviewChildAdapter(callback)
                itemView.nested_recycler.adapter = adapter
                adapter.submitList(data.list)
            }
            is DoctorParent -> {
                itemView.nested_header.text = data.header
                val adapter = OverviewChildAdapter(callback)
                itemView.nested_recycler.adapter = adapter
                adapter.submitList(data.list)
            }

            is PromoParent -> {
                val adapter = OverviewChildAdapter(callback)
                itemView.nested_viewpager.adapter = adapter
                adapter.submitList(data.list)
            }
        }
    }
}
