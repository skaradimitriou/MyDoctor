package com.stathis.mydoctor.features.main.overview

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment


class OverviewFragment : AbstractFragment(R.layout.fragment_overview) {

    private lateinit var viewModel : OverviewViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun running() {
        viewModel.doctorList.observe(this, Observer{
            Log.d("TAG", it.toString())
        })
    }

    override fun stopped() {
        viewModel.doctorList.removeObservers(this)
    }
}