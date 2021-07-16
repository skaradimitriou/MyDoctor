package com.stathis.mydoctor.features.profile.adapter

import android.view.View
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.profile.model.ProfileItem
import kotlinx.android.synthetic.main.holder_profile_detail_item_row.view.*

class ProfileViewHolder(itemView : View) : AbstractViewHolder(itemView) {

    override fun present(data: LocalModel) {
        when(data){
            is ProfileItem -> {
                itemView.profile_item_header.text = data.header
                itemView.profile_item_value.text = data.value
            }
        }
    }
}