package com.stathis.mydoctor.features.main.search

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractFragment
import com.stathis.mydoctor.callbacks.SearchClickListener
import com.stathis.mydoctor.models.Category
import com.stathis.mydoctor.models.Doctor
import com.stathis.mydoctor.models.Query
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : AbstractFragment(R.layout.fragment_search) {

    private lateinit var viewModel : SearchViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

    /*
        As a user I want to:

            - Be able to search doctors by name (?) - fullname (?)
            - Be able to view my recent queries
            - Be able to search for a category in a specific location (?)
         */
    }

    override fun running() {
        manageSearch()

        search_recycler.adapter = viewModel.adapter

        viewModel.bindCallbacks(object : SearchClickListener{
            override fun onDoctorTap(doc: Doctor) {
                //gotoDoctor
            }

            override fun onCategoryTap(category: Category) {
                //load category results
            }

            override fun onQueryTap(query: Query) {
                // load results for this query
            }
        })

        viewModel.observe(this)
    }

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

                //viewModel.saveQueryToDatabase()
                viewModel.getResultsForQuery(query.toString())

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun stopped() = viewModel.release(this)
}