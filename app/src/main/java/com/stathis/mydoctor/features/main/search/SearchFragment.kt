package com.stathis.mydoctor.features.main.search

import android.content.Intent
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment
import com.stathis.mydoctor.callbacks.SearchClickListener
import com.stathis.mydoctor.features.doctor.DoctorActivity
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.Query
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : AbstractFragment(R.layout.fragment_search) {

    private lateinit var viewModel : SearchViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun running() {
        manageSearch()

        search_recycler.adapter = viewModel.adapter

        viewModel.bindCallbacks(object : SearchClickListener{
            override fun onDoctorTap(doc: Doctor) = openDoctorScreen(doc)
            override fun onQueryTap(query: Query) = viewModel.getResultsForQuery(query.query)
        })

        viewModel.observe(this)
    }

    override fun stopped() = viewModel.release(this)

    private fun manageSearch() {
        search_searchbar.setOnClickListener {
            search_searchbar.isIconified = false
        }

        search_searchbar.clearFocus()

        search_searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_searchbar.clearFocus()
                search_searchbar.setQuery("", false)
                Log.d("HELLO", query.toString())

                viewModel.insertQueryToDb(Query(query.toString()))

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun openDoctorScreen(doctor: Doctor) {
        val model = Gson().toJson(doctor)
        startActivity(Intent(requireContext(), DoctorActivity::class.java).also{
            it.putExtra("DOCTOR",model)
        })
    }
}