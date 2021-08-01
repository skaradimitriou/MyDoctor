package com.stathis.mydoctor.features.main.search.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.main.search.models.EmptyQuery
import com.stathis.mydoctor.features.main.search.models.EmptyResult
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.Query
import kotlinx.android.synthetic.main.holder_doctor_results_item.view.*
import kotlinx.android.synthetic.main.holder_no_results_found_item.view.*
import kotlinx.android.synthetic.main.holder_query_item.view.*

class SearchParentViewHolder(itemView : View, callback : ItemClickListener) : AbstractViewHolder(itemView,callback) {

    override fun present(data: LocalModel) {
        when(data){
            is Doctor -> {
                itemView.doctor_name.text = data.fullname
                itemView.doctor_category.text = data.category
                itemView.doctor_rating.rating = data.rating.toFloat()
                itemView.doctor_experience.text = "${data.experience.toString()} years"

                Glide.with(itemView).load(data.image).into(itemView.doctor_img)
            }

            is Query -> itemView.search_results_item_label.text = data.query
            is EmptyQuery -> bindErrorMessage(data.headerMessage,data.description)
            is EmptyResult -> bindErrorMessage(data.headerMessage,data.description)
        }
    }

    private fun bindErrorMessage(headerMsg : String, description : String) {
        itemView.message_header.text = headerMsg
        itemView.message_desc.text = description
    }
}