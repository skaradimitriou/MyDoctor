package com.stathis.mydoctor.features.caterories

import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.features.caterories.adapter.CategoriesAdapter
import com.stathis.mydoctor.features.caterories.model.DoctorCategory

class CategoriesViewModel : ViewModel(), ItemClickListener {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    val adapter = CategoriesAdapter(this)
    val categories = MutableLiveData<List<DoctorCategory>>()

    init {
        getCategories()
    }

    private fun getCategories(){
        firestore.collection("categories").get().addOnSuccessListener { docs ->
            val categoryList = arrayListOf<DoctorCategory>()
            for (document in docs) {
                Log.d("TAG", "${document.id} => ${document.data}")
                val category = document.toObject(DoctorCategory::class.java)
                categoryList.add(category)
            }

            categories.value = categoryList
        }.addOnFailureListener {
            Log.d("TAG", "Error getting documents: ", it)
        }
    }

    fun observe(owner : LifecycleOwner){
        categories.observe(owner, Observer {
            adapter.submitList(it)
        })
    }

    fun release(owner : LifecycleOwner){
        categories.removeObservers(owner)
    }

    override fun onItemTap(view: View) {
        when(view.tag){
            is DoctorCategory -> {} //handle click later
        }
    }
}
