package com.stathis.mydoctor.features.main.search

import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.gson.Gson
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.callbacks.SearchClickListener
import com.stathis.mydoctor.features.main.search.adapter.SearchParentAdapter
import com.stathis.mydoctor.features.main.search.models.EmptyQuery
import com.stathis.mydoctor.features.main.search.models.EmptyResult
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.EmptyModel
import com.stathis.mydoctor.models.Query
import com.stathis.mydoctor.utils.TAG

class SearchViewModel : ViewModel(), ItemClickListener {

    val firestore = FirebaseFirestore.getInstance()
    val auth by lazy { FirebaseAuth.getInstance() }
    val adapter = SearchParentAdapter(this)
    val data = MutableLiveData<List<LocalModel>>()
    private var queryList = mutableListOf<LocalModel>()
    private lateinit var callback: SearchClickListener

    init {
        getUserQueries()
    }

    fun bindCallbacks(callback: SearchClickListener) {
        this.callback = callback
    }

    fun getUserQueries() {
        firestore.collection("saved_queries")
            .document(auth.currentUser!!.uid).addSnapshotListener { p0, p1 ->
                Log.d("", p0?.data.toString())

                p0?.data?.toList()?.forEach {
                    Log.d("", it.first.toString())
                    Log.d("", it.second.toString())

                    val json = Gson().toJsonTree(it.second)
                    Log.d("", json.toString())
                    queryList = Gson().fromJson(json, Array<Query>::class.java).toMutableList()

                    Log.d("", queryList.toString())
                }

                bindUserQueries()
            }
    }

    private fun bindUserQueries() {
        when(queryList.isEmpty()){
            true -> adapter.submitList(listOf(EmptyQuery("You have no queries yet","We couldn't find your queries. Once you search for something it will appear here.")))
            false -> adapter.submitList(queryList)
        }

        adapter.notifyDataSetChanged()
    }

    fun insertQueryToDb(query: Query) {
        val docRef = firestore.collection("saved_queries").document(auth.currentUser!!.uid)

        when (queryList.size >= 9) {
            true -> {
                queryList.remove(queryList.last())
                queryList.add(0, query)
            }
            false -> queryList.add(0, query)
        }


        val data = hashMapOf<String, Any>(
            "queryList" to queryList
        )

        docRef.set(data).addOnSuccessListener {
            Log.d("TAG", "OK")
        }.addOnFailureListener {
            Log.d("TAG", "NOT OK")
        }
        getUserQueries()
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
        }
    }

    fun observe(owner: LifecycleOwner) {
        data.observe(owner, Observer {
            Log.d(TAG, it.toString())

            when(it.isEmpty()){
                true -> adapter.submitList(listOf(EmptyResult("No results found","We couldn't find results for this doctor. Please try again")))
                false -> adapter.submitList(it)
            }
        })
    }

    fun release(owner: LifecycleOwner) = data.removeObservers(owner)

    override fun onItemTap(view: View) = when (view.tag) {
            is Query -> callback.onQueryTap(view.tag as Query)
            is Doctor -> callback.onDoctorTap(view.tag as Doctor)
            else -> Unit
    }
}
