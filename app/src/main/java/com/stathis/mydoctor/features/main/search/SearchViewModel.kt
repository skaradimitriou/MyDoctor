package com.stathis.mydoctor.features.main.search

import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.callbacks.SearchClickListener
import com.stathis.mydoctor.features.main.search.adapter.SearchParentAdapter
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.EmptyModel
import com.stathis.mydoctor.models.Query
import com.stathis.mydoctor.utils.TAG

class SearchViewModel : ViewModel(), ItemClickListener {

    val firestore = FirebaseFirestore.getInstance()
    val adapter = SearchParentAdapter(this)
    val data = MutableLiveData<List<LocalModel>>()
    private lateinit var callback: SearchClickListener

    init {
        getUserQueries()
    }

    private fun getUserQueries() {
        val queryList = listOf(
            Query("doctor 1"),
            Query("doctor 2"),
            Query("doctor 3"),
            Query("doctor 4"),
            Query("doctor 5")
        )

        adapter.submitList(queryList)
    }

    fun bindCallbacks(callback: SearchClickListener) {
        this.callback = callback
    }

    fun observe(owner: LifecycleOwner) {
        data.observe(owner, Observer {
            Log.d(TAG, it.toString())
            adapter.submitList(it)
        })
    }

    fun getResultsForQuery(query: String) {
        firestore.collection("doctors").get(Source.SERVER).addOnSuccessListener { docs ->
            val doctors = arrayListOf<Doctor>()
            for (document in docs) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val doctor = document.toObject(Doctor::class.java)

                when (doctor.fullname.contains(query)) {
                    true -> doctors.add(doctor)
                    false -> Unit
                }
            }

            data.value = doctors
        }.addOnFailureListener {
            Log.d(TAG, "Error getting documents: ", it)
            bindErrorCase()
        }
    }

    private fun bindErrorCase() {
        /** @Author: Stathis
        Logic : Show error message in recyclerview
         */

        //adapter.submitList(listOf(EmptyModel("No results found")))
    }

    fun release(owner: LifecycleOwner) = data.removeObservers(owner)

    override fun onItemTap(view: View) {
        when (view.tag) {
            is Query -> callback.onQueryTap(view.tag as Query)
            is Category -> callback.onCategoryTap(view.tag as Category)
            is Doctor -> callback.onDoctorTap(view.tag as Doctor)
        }
    }
}