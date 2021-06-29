package com.stathis.mydoctor.features.main.overview

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment
import com.stathis.mydoctor.features.caterories.CategoriesActivity
import com.stathis.mydoctor.features.caterories.adapter.CategoriesAdapter
import kotlinx.android.synthetic.main.fragment_overview.*


class OverviewFragment : AbstractFragment(R.layout.fragment_overview) {

    private lateinit var viewModel : OverviewViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun running() {
        /*
        Tasks :
            - insert all doctor categories to firestore db - DONE
            - get the 10 most common doctor categories
            - get 10 dummy doctors in main screen
            - add a header for this screen that contains a search input field
         */

        categories_button.setOnClickListener{
            startActivity(Intent(requireContext(), CategoriesActivity::class.java))
        }

        viewModel.doctorList.observe(this, Observer{
            Log.d("TAG", it.toString())
        })
    }

    override fun stopped() {
        viewModel.doctorList.removeObservers(this)
    }
}