package com.stathis.mydoctor.features.register

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.main.MainActivity
import com.stathis.mydoctor.features.onboarding.OnboardingActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AbstractActivity(R.layout.activity_register) {

    private lateinit var viewModel: RegisterViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun running() {
        register_btn.setOnClickListener {
            val email = email_input_field.text.toString()
            val pass = pass_input_field.text.toString()
            val pass_conf = pass_conf_input_field.text.toString()

            when(pass == pass_conf){
                true -> viewModel.validateData(email,pass)
                false -> Unit //handle error case with appropriate messages
            }
        }

        viewModel.userRegisted.observe(this, Observer {
            when(it){
                true -> {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                    finish()
                }
                false -> {} // throw some kind of error
            }
        })
    }

    override fun stopped() = viewModel.userRegisted.removeObservers(this)
}