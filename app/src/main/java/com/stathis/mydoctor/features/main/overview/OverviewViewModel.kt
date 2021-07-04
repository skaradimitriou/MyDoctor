package com.stathis.mydoctor.features.main.overview

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.callbacks.HomeClickListener
import com.stathis.mydoctor.features.main.overview.adapters.OverviewScreenAdapter
import com.stathis.mydoctor.features.main.overview.model.CategoryParent
import com.stathis.mydoctor.features.main.overview.model.DoctorParent
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.HeaderModel

class OverviewViewModel(app : Application) : AndroidViewModel(app), ItemClickListener {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    val resources = app.resources

    val adapter = OverviewScreenAdapter(this)
    val overviewList = MutableLiveData<List<LocalModel>>()
    private lateinit var callback : HomeClickListener

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

    fun bindCallbacks(callback : HomeClickListener){
        this.callback = callback
    }

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
            Category(resources.getString(R.string.pathologos), R.drawable.ic_stethoscope),
            Category(resources.getString(R.string.dermatologos), R.drawable.ic_dermatologist),
            Category(resources.getString(R.string.ofthalmiatros), R.drawable.ic_ophthalmologist),
            Category(resources.getString(R.string.orthopedikos), R.drawable.ic_orthopedics),
            Category(resources.getString(R.string.kardiologos), R.drawable.ic_cardiologist),
            Category(resources.getString(R.string.wrl), R.drawable.ic_wrl),
            Category(resources.getString(R.string.xeirourgos), R.drawable.ic_surgery),
            Category(resources.getString(R.string.neurologos), R.drawable.ic_neurology)
        )

        val list = listOf(
            HeaderModel(),
            CategoryParent(resources.getString(R.string.doctors_category), categories),
            DoctorParent(resources.getString(R.string.doctors_header), doctorList)
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
            is Doctor -> callback.onDoctorTap(view.tag as Doctor)
            is Category -> callback.onCategoryTap(view.tag as Category)
        }
    }
}