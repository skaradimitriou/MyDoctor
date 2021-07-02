package com.stathis.mydoctor.features.main.overview.adapters

import android.view.View
import com.bumptech.glide.Glide
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import kotlinx.android.synthetic.main.holder_home_category_item.view.*
import kotlinx.android.synthetic.main.holder_home_doctor_card_item.view.*

class OverviewChildViewHolder(itemView : View, callback : ItemClickListener) : AbstractViewHolder(itemView,callback) {

    override fun present(data: LocalModel) {
        when(data){
            is Doctor -> {
                Glide.with(itemView).load(data.image).into(itemView.home_doc_img)
                itemView.doctor_name.text = "Dr. ${data.fullname}"
                itemView.doctor_category.text = data.category
                itemView.doctor_experience.text = data.experience.toString()
                itemView.doctor_rating.rating = data.rating.toFloat()
            }

            is Category -> {
                itemView.home_category_img.setImageResource(data.image)
                itemView.home_category.text = data.description
            }
        }
    }
}