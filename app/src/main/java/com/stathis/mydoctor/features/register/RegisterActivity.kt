package com.stathis.mydoctor.features.register

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.main.MainActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AbstractActivity(R.layout.activity_register) {

    private lateinit var viewModel: RegisterViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        /*
        As a user, I want to have a clean screen with the following options:
        - Enter my Register data (email, password,pass confirm)
        - Register Button

        Also: The following functionalities need to be implemented:
        - Validate user input
        - Check if passwords match and if it is a strong password
        - Authenticate user to the app

         */
    }

    override fun running() {
        val email = "admin@gmail.com"
        val pass = "123456"
        val pass_conf = "123456"

        register_btn.setOnClickListener {
            when(pass == pass_conf){
                true -> viewModel.validateData(email,pass)
                false -> Unit //handle error case with appropriate messages
            }
        }

        viewModel.userRegisted.observe(this, Observer {
            when(it){
                true -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                false -> {} // throw some kind of error
            }
        })
    }

    override fun stopped() = viewModel.userRegisted.removeObservers(this)
}