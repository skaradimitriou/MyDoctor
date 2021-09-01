package com.stathis.mydoctor.features.forgotpassword

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import de.mateware.snacky.Snacky
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AbstractActivity(R.layout.activity_forgot_password) {

    private lateinit var viewModel : ForgotPasswordViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
    }

    override fun running() {
        send_activation_email.setOnClickListener {
            viewModel.verifyEmail(forgot_email_field.text.toString())
        }

        viewModel.userVerified.observe(this, Observer {
            when (it) {
                true -> showPopup("An e-mail has been sent to the above address.")
                false -> showError("An error has occured. Please try again")
            }
        })
    }

    override fun stopped() {
        viewModel.userVerified.removeObservers(this)
    }
    private fun showPopup(message : String) {
        Snacky.builder().setView(findViewById(R.id.forgot_pass_screen)).success().setText(message)
            .show()

        Handler(Looper.getMainLooper()).postDelayed({
            onBackPressed()
            finish()
        }, 3000)
    }

    private fun showError(message : String) {
        Snacky.builder().setView(findViewById(R.id.forgot_pass_screen)).error().setText(message)
            .show()
    }
}