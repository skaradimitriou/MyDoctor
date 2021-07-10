package com.stathis.mydoctor.features.main.overview.adapters

import android.view.View
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.main.overview.model.CategoryParent
import com.stathis.mydoctor.features.main.overview.model.DoctorParent
import com.stathis.mydoctor.features.main.overview.model.PromoParent
import com.stathis.mydoctor.models.HeaderModel
import kotlinx.android.synthetic.main.holder_home_header_item.view.*
import kotlinx.android.synthetic.main.holder_nested_parent_item.view.*
import kotlinx.android.synthetic.main.holder_nested_parent_viewpager.view.*

class OverviewScreenViewHolder(itemView : View,callback : ItemClickListener) : AbstractViewHolder(itemView,callback){

    private val callback = callback

    override fun present(data: LocalModel) {
        when(data){
            is HeaderModel -> {
                itemView.home_header.text = "Hello ${data.user.username}"
                Glide.with(itemView).load(data.user.userPhoto).into(itemView.home_user_img)
            }

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

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(20))
                compositePageTransformer.addTransformer { page: View, position: Float ->
                    val r = 1 - Math.abs(position)
                    page.scaleY = 0.85f + r * 0.15f
                }

                itemView.nested_viewpager.setPageTransformer(compositePageTransformer)
                itemView.nested_viewpager.offscreenPageLimit = 3

                adapter.submitList(data.list)

                itemView.nested_viewpager.currentItem = 1
            }
        }
    }
}
