package com.stathis.mydoctor.features.main.favorites

import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : AbstractFragment(R.layout.fragment_favorites) {

    private lateinit var viewModel: FavoritesViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

    override fun running() {
        favorites_screen_recycler.adapter = viewModel.adapter
        viewModel.observe(this)
    }

    override fun stopped() = viewModel.release(this)
}