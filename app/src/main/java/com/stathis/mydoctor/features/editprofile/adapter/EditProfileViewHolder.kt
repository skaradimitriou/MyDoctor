package com.stathis.mydoctor.features.editprofile.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.profile.model.ProfileItem
import kotlinx.android.synthetic.main.holder_profile_detail_item_row.view.*

class EditProfileViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    fun present(data : LocalModel, editableMode : Boolean){
        when(data){
            is ProfileItem -> {
                itemView.profile_item_header.text = data.header
                itemView.profile_item_value.text = data.value

                when(editableMode){
                    true -> {
                        itemView.profile_item_value.isEnabled = true
                    }

                    false -> {
                        itemView.profile_item_value.isEnabled = false
                    }
                }
            }
        }
    }
}