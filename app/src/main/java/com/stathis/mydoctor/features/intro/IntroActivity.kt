package com.stathis.mydoctor.features.intro

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.login.LoginActivity
import com.stathis.mydoctor.features.main.MainActivity
import com.stathis.mydoctor.features.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AbstractActivity(R.layout.activity_intro) {

    private lateinit var viewModel: IntroViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(IntroViewModel::class.java)

        /*
        As a user, I want to have a clean screen with the following options:

        - Login Button
        - Register Button

        Also: The following functionalities need to be implemented:
        - Check if user is logged in, then redirect him to main screen

         */
    }

    override fun running() {
        viewModel.userIsLoggedIn.observe(this, Observer{
            when(it){
                true -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                false -> Unit
            }
        })

        login_btn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        register_btn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun stopped() = viewModel.userIsLoggedIn.removeObservers(this)
}