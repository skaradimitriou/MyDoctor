package com.stathis.mydoctor.features.main.appointments

import android.view.View
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.stathis.mydoctor.abstraction.ItemClickListener
import com.stathis.mydoctor.callbacks.AppointmentClickListener
import com.stathis.mydoctor.features.main.appointments.adapter.AppointmentsAdapter
import com.stathis.mydoctor.models.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppointmentsViewModel : ViewModel(), ItemClickListener {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val list = MutableLiveData<List<Appointment>>()
    val adapter = AppointmentsAdapter(this)
    private lateinit var callback : AppointmentClickListener

    init {
        startShimmer()

        viewModelScope.launch{
            delay(500)
            getUserAppointments()
        }
    }

    private fun startShimmer() {
        adapter.submitList(listOf(ShimmerObject(),ShimmerObject(),ShimmerObject(),ShimmerObject(),ShimmerObject()))
    }

    private fun getUserAppointments() {
        firestore.collection("appointments")
            .document(auth.currentUser!!.uid).addSnapshotListener { p0, p1 ->
                p0?.data?.toList()?.forEach {
                    val json = Gson().toJsonTree(it.second)
                    val userAppointments = Gson().fromJson(json, Array<Appointment>::class.java).toMutableList()
                    list.value = userAppointments
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
