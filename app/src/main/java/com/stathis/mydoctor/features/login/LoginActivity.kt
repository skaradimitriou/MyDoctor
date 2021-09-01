package com.stathis.mydoctor.features.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.forgotpassword.ForgotPasswordActivity
import com.stathis.mydoctor.features.main.MainActivity
import com.stathis.mydoctor.features.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AbstractActivity(R.layout.activity_login) {

    private lateinit var viewModel : LoginViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun running() {
        login_btn.setOnClickListener{
            val email = email_input_field.text.toString()
            val pass = pass_input_field.text.toString()

            viewModel.authenticateUser(email,pass)
        }

        forgot_password.setOnClickListener{
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        register_btn.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        viewModel.userAuthenticated.observe(this, Observer {
            when(it){
                true -> startActivity(Intent(this, MainActivity::class.java))
                false -> {} // throw some kind of error to the user
            }
        })

        viewModel.userIsLoggedIn.observe(this, Observer{
            when(it){
                true -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                false -> Unit
            }
        })
    }

    override fun stopped() = viewModel.userAuthenticated.removeObservers(this)
}