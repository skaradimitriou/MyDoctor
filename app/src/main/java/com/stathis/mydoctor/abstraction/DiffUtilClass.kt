package com.stathis.mydoctor.abstraction

import androidx.recyclerview.widget.DiffUtil

class DiffUtilClass<T : LocalModel> : DiffUtil.ItemCallback<T>() {
    
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.equals(newItem)
    }
}