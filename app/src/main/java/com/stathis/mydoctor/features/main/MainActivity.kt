package com.stathis.mydoctor.features.main

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity

class MainActivity : AbstractActivity(R.layout.activity_main) {

    override fun init() {
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun running() {}

    override fun stopped() {}

    override fun onBackPressed() {} // Logout pls
}