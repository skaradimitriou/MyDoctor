package com.stathis.mydoctor.features.results

import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import kotlinx.android.synthetic.main.activity_doctor_results.*

class DoctorResultsActivity : AbstractActivity(R.layout.activity_doctor_results) {

    override fun init() {}

    override fun running() {
        val category = intent.getStringExtra("CATEGORY")
        doctor_results_txt.text = category
    }

    override fun stopped() {}
}