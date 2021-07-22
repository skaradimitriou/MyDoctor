package com.stathis.mydoctor.features.main.appointments

import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.callbacks.AppointmentClickListener
import com.stathis.mydoctor.features.main.appointments.adapter.AppointmentsAdapter
import com.stathis.mydoctor.models.*
import com.stathis.mydoctor.utils.TAG

class AppointmentsViewModel : ViewModel(), ItemClickListener {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val list = MutableLiveData<List<Appointment>>()
    val adapter = AppointmentsAdapter(this)
    private lateinit var callback : AppointmentClickListener

    init {
        getAppointments()
    }

    private fun getAppointments(){
        firestore.collection("appointments").document(auth.currentUser!!.uid).get()
            .addOnCompleteListener { task ->
                when (task.isSuccessful) {
                    true -> {
                        val result = task.result
                        val appointment = result?.toObject(TestAppointment::class.java)

                        val test = result?.get("appointment") as HashMap<String,Doctor>

                        Log.d("","")
                    }
                    false -> Unit
                }
        }
    }

    fun bindCallbacks(callback : AppointmentClickListener) {
        this.callback = callback
    }

    fun observe(owner : LifecycleOwner){
        list.observe(owner, Observer{
            when(it.isNullOrEmpty()){
                true -> submitEmptyList()
                false -> adapter.submitList(it)
            }
        })
    }

    private fun submitEmptyList() {
        adapter.submitList(listOf(EmptyModel("You have no appointments yet.")))
    }

    fun release(owner: LifecycleOwner) = list.removeObservers(owner)
    override fun onItemTap(view: View) {
        when(view.tag){
            is Appointment -> callback.onAppointmentTap(view.tag as Appointment)
        }
    }
}
