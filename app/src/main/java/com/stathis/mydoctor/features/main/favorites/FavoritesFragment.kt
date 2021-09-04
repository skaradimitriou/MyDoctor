package com.stathis.mydoctor.features.main.favorites

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment
import com.stathis.mydoctor.callbacks.DoctorClickListener
import com.stathis.mydoctor.features.doctor.DoctorActivity
import com.stathis.mydoctor.models.Doctor
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : AbstractFragment(R.layout.fragment_favorites) {

    private lateinit var viewModel: FavoritesViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

    override fun running() {
        viewModel.addCallback(object : DoctorClickListener{
            override fun onDoctorTap(doctor: Doctor) {
                val model = Gson().toJson(doctor)
                startActivity(Intent(requireContext(), DoctorActivity::class.java).also{
                    it.putExtra("DOCTOR",model)
                })
            }
        })

        favorites_screen_recycler.adapter = viewModel.adapter
        viewModel.observe(this)
    }

    override fun stopped() = viewModel.release(this)
}