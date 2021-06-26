package com.stathis.mydoctor.features.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AbstractActivity(R.layout.activity_login) {

    private lateinit var viewModel : LoginViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        /*
        As a user, I want to have a clean screen with the following options:
        - Enter my login data (email, password)
        - Login Button

        Also: The following functionalities need to be implemented:
        - Validate user input
        - Authenticate user to the app

         */
    }

    override fun running() {
        login_btn.setOnClickListener{
            val email = email_input_field.text.toString()
            val pass = pass_input_field.text.toString()

            viewModel.authenticateUser(email,pass)
        }

        viewModel.userAuthenticated.observe(this, Observer {
            when(it){
                true -> startActivity(Intent(this, MainActivity::class.java))
                false -> {} // throw some kind of error to the user
            }
        })
    }

    override fun stopped() = viewModel.userAuthenticated.removeObservers(this)
}