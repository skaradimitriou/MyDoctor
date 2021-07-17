package com.stathis.mydoctor.features.results.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.main.search.models.EmptyResult
import com.stathis.mydoctor.models.Doctor
import kotlinx.android.synthetic.main.holder_doctor_results_item.view.*
import kotlinx.android.synthetic.main.holder_no_results_found_item.view.*

class DoctorResultsViewHolder(itemView : View, callback : ItemClickListener) : AbstractViewHolder(itemView,callback) {

    override fun present(data: LocalModel) {
        when(data){
            is Doctor -> {
                itemView.doctor_name.text = data.fullname
                itemView.doctor_category.text = data.category
                itemView.doctor_rating.rating = data.rating.toFloat()
                itemView.doctor_experience.text = data.experience.toString()

                Glide.with(itemView).load(data.image).into(itemView.doctor_img)
            }

            is EmptyResult -> {
                itemView.message_header.text = data.headerMessage
                itemView.message_desc.text = data.description
            }
        }
    }
}