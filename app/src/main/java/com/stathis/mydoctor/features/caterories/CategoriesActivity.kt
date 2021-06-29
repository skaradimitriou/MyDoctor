package com.stathis.mydoctor.features.caterories

import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AbstractActivity(R.layout.activity_categories) {

    private lateinit var viewModel : CategoriesViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
    }

    override fun running() {
        categories_recycler.adapter = viewModel.adapter
        viewModel.observe(this)
    }

    override fun stopped() = viewModel.release(this)
}