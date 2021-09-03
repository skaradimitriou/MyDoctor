package com.stathis.mydoctor.features.main.favorites

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.stathis.mydoctor.abstraction.AbstractAndroidViewModel
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.features.results.adapter.DoctorResultsAdapter
import com.stathis.mydoctor.models.Doctor

class FavoritesViewModel(app : Application) : AbstractAndroidViewModel(app), ItemClickListener{

    val firestore by lazy { FirebaseFirestore.getInstance() }
    val auth by lazy { FirebaseAuth.getInstance() }
    val favorites = MutableLiveData<List<Doctor>>()
    private var favoriteList = mutableListOf<Doctor>()
    val adapter = DoctorResultsAdapter(this)

    init{
        getUserFavorites()
    }

    private fun getUserFavorites(){
        firestore.collection("favorites")
            .document(auth.currentUser!!.uid).addSnapshotListener { p0, p1 ->
                Log.d("", p0?.data.toString())

                p0?.data?.toList()?.forEach {
                    val json = Gson().toJsonTree(it.second)
                    favoriteList = Gson().fromJson(json, Array<Doctor>::class.java).toMutableList()

                    Log.d("FAVORITE_LIST", favoriteList.toString())
                }

                favorites.value = favoriteList
            }
    }

    fun observe(owner :LifecycleOwner){
        favorites.observe(owner, Observer{
            adapter.submitList(it)
        })
    }

    fun release(owner : LifecycleOwner){
        favorites.removeObservers(owner)
    }

    override fun onItemTap(view: View) {
        when(view.tag){
            is Doctor -> {
                Log.d("","")
            }
        }
    }
}