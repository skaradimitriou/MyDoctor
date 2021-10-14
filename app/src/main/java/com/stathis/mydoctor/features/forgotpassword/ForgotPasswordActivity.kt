package com.stathis.mydoctor.features.forgotpassword

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.utils.MySnackbars
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
                true -> showPopup(getString(R.string.forgot_pass_email_sent))
                false -> showError(getString(R.string.login_error_occured))
            }
        })
    }

    override fun stopped() {
        viewModel.userVerified.removeObservers(this)
    }
    private fun showPopup(message : String) {
        MySnackbars().successSnack(findViewById(R.id.forgot_pass_screen),message)
        Handler(Looper.getMainLooper()).postDelayed({
            onBackPressed()
            finish()
        }, 3000)
    }

    private fun showError(message : String) = MySnackbars().errorSnack(findViewById(R.id.forgot_pass_screen),message)

}