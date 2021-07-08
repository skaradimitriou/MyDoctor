package com.stathis.mydoctor.features.main.search

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.callbacks.SearchClickListener

class SearchViewModel : ViewModel(), ItemClickListener {

    //val adapter = SearchParentAdapter(this)
    val data = MutableLiveData<List<LocalModel>>()
    private lateinit var callback : SearchClickListener

    init{
        getUserQueries()
    }

    private fun getUserQueries(){

    }

    fun bindCallbacks(callback : SearchClickListener) {
        this.callback = callback
    }

    fun observe(owner: LifecycleOwner){
        data.observe(owner, Observer{
            //adapter.submitList(it)
        })
    }

    fun release(owner : LifecycleOwner) = data.removeObservers(owner)
    override fun onItemTap(view: View) {
        when(view.tag){
            //is Query -> {}
            //is Category -> {}
            //is Doctor -> {}
        }
    }
}
