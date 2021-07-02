package com.stathis.mydoctor.features.main.overview

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.main.overview.adapters.OverviewScreenAdapter
import com.stathis.mydoctor.features.main.overview.model.CategoryParent
import com.stathis.mydoctor.features.main.overview.model.DoctorParent
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.HeaderModel

class OverviewViewModel : ViewModel(), ItemClickListener {

    private val firestore by lazy { FirebaseFirestore.getInstance() }

    val adapter = OverviewScreenAdapter(this)
    val overviewList = MutableLiveData<List<LocalModel>>()

    init {
        initDummyList()
    }

//    fun getDoctors() {
//        firestore.collection("doctors").get(Source.SERVER).addOnSuccessListener { docs ->
//            val doctors = arrayListOf<Doctor>()
//            for (document in docs) {
//                Log.d("TAG", "${document.id} => ${document.data}")
//                val doctor = document.toObject(Doctor::class.java)
//                doctors.add(doctor)
//            }
//
//            doctorList.value = doctors
//        }.addOnFailureListener {
//            Log.d("TAG", "Error getting documents: ", it)
//        }
//    }

    fun initDummyList() {
        val doctorList = listOf(
            Doctor("Γιάννης Παπαδόπουλος", "https://post.medicalnewstoday.com/wp-content/uploads/2019/01/Male_Doctor_732x549-thumbnail.jpg", 4.6, "6934567143", "Ορθοπεδικός", 10),
            Doctor("Γιάννης Παπαδόπουλος", "https://post.medicalnewstoday.com/wp-content/uploads/2019/01/Male_Doctor_732x549-thumbnail.jpg", 4.6, "6934567143", "Ορθοπεδικός", 10),
            Doctor("Γιάννης Παπαδόπουλος", "https://post.medicalnewstoday.com/wp-content/uploads/2019/01/Male_Doctor_732x549-thumbnail.jpg", 4.6, "6934567143", "Ορθοπεδικός", 10),
            Doctor("Γιάννης Παπαδόπουλος", "https://post.medicalnewstoday.com/wp-content/uploads/2019/01/Male_Doctor_732x549-thumbnail.jpg", 4.6, "6934567143", "Ορθοπεδικός", 10),
            Doctor("Γιάννης Παπαδόπουλος", "https://post.medicalnewstoday.com/wp-content/uploads/2019/01/Male_Doctor_732x549-thumbnail.jpg", 4.6, "6934567143", "Ορθοπεδικός", 10),
            Doctor("Γιάννης Παπαδόπουλος", "https://post.medicalnewstoday.com/wp-content/uploads/2019/01/Male_Doctor_732x549-thumbnail.jpg", 4.6, "6934567143", "Ορθοπεδικός", 10)
        )

        val categories = listOf(
            Category("Παθολόγος", R.mipmap.ic_launcher),
            Category("Δερματολόγος", R.mipmap.ic_launcher),
            Category("Οφθαλμίατρος", R.mipmap.ic_launcher),
            Category("Ορθοπεδικός", R.mipmap.ic_launcher),
            Category("Καρδιολόγος", R.mipmap.ic_launcher),
            Category("Ω.Ρ.Λ.", R.mipmap.ic_launcher),
            Category("Χειρούργος", R.mipmap.ic_launcher),
            Category("Νευρολόγος", R.mipmap.ic_launcher)
        )

        val list = listOf(
            HeaderModel(),
            CategoryParent("Categories", categories),
            DoctorParent("Doctors", doctorList)
        )

        overviewList.value = list
    }

    fun observe(owner : LifecycleOwner){
        overviewList.observe(owner, Observer{
            adapter.submitList(it)
        })
    }

    fun release(owner : LifecycleOwner) = overviewList.removeObservers(owner)

    override fun onItemTap(view: View) {
        when(view.tag){
            //
        }
    }
}