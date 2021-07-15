package com.stathis.mydoctor.features.main.overview

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.callbacks.HomeClickListener
import com.stathis.mydoctor.features.main.overview.adapters.OverviewScreenAdapter
import com.stathis.mydoctor.features.main.overview.model.AllCategoriesModel
import com.stathis.mydoctor.features.main.overview.model.CategoryParent
import com.stathis.mydoctor.features.main.overview.model.DoctorParent
import com.stathis.mydoctor.features.main.overview.model.PromoParent
import com.stathis.mydoctor.models.*
import com.stathis.mydoctor.utils.DEFAULT_IMG
import com.stathis.mydoctor.utils.DEFAULT_USERNAME
import com.stathis.mydoctor.utils.TAG

class OverviewViewModel(app : Application) : AndroidViewModel(app), ItemClickListener {

    val resources = app.resources
    private val firestore by lazy { FirebaseFirestore.getInstance() }
    val adapter = OverviewScreenAdapter(this)
    val overviewList = MutableLiveData<List<LocalModel>>()
    private lateinit var callback : HomeClickListener

    init {
        initDummyList(User(DEFAULT_USERNAME, DEFAULT_IMG))
        getUserData()
    }

    fun bindCallbacks(callback : HomeClickListener){
        this.callback = callback
    }

    fun initDummyList(user : User) {
        val promoList = listOf(
            PromoItem("This is a header","", R.drawable.ic_stethoscope),
            PromoItem("This is a header","",R.drawable.ic_stethoscope),
            PromoItem("This is a header","", R.drawable.ic_stethoscope))

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
            Category(resources.getString(R.string.neurologos), R.drawable.ic_neurology),
            AllCategoriesModel()
        )

        val list = listOf(
            HeaderModel(user),
            PromoParent(promoList),
            CategoryParent(resources.getString(R.string.doctors_category), categories),
            DoctorParent(resources.getString(R.string.doctors_header), doctorList)
        )

        overviewList.value = list
    }

    private fun getUserData() {
        firestore.collection("users").document("uCAIilCFU0673H3S3Lyo").get()
            .addOnCompleteListener { task ->
                when (task.isSuccessful) {
                    true -> {
                        val result = task.result
                        Log.d(TAG, "${result?.id} => ${result?.data}")
                        val user = result!!.toObject(User::class.java)!!

                        initDummyList(user)
                    }
                    false -> {
                        // handle this
                        Log.d(TAG, task.toString())
                    }
                }
            }
    }

    fun observe(owner : LifecycleOwner){
        overviewList.observe(owner, Observer{
            adapter.submitList(it)
        })
    }

    fun release(owner : LifecycleOwner) = overviewList.removeObservers(owner)

    override fun onItemTap(view: View) {
        when(view.tag){
            is HeaderModel -> callback.onProfileTap()
            is Doctor -> callback.onDoctorTap(view.tag as Doctor)
            is Category -> callback.onCategoryTap(view.tag as Category)
            is AllCategoriesModel -> callback.openAllCategories(view.tag as AllCategoriesModel)
        }
    }
}