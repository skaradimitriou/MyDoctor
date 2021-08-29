package com.stathis.mydoctor.features.editprofile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.DiffUtilClass
import com.stathis.mydoctor.abstraction.LocalModel

class EditProfileAdapter() : ListAdapter<LocalModel, EditProfileViewHolder>(DiffUtilClass<LocalModel>()) {

    private var editableMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_editprofile_item, parent, false)
        return EditProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditProfileViewHolder, position: Int) {
        holder.present(getItem(position),editableMode)
    }

    fun changeEditMode(editableMode: Boolean) {
        this.editableMode = editableMode
        notifyDataSetChanged()
    }
}