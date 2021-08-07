package com.stathis.mydoctor.features.onboarding

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractActivity
import com.stathis.mydoctor.features.main.MainActivity
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AbstractActivity(R.layout.activity_onboarding) {

    private lateinit var viewModel : OnboardingViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(OnboardingViewModel::class.java)
    }

    override fun running() {
        onboarding_viewpager.adapter = viewModel.adapter

        next_btn.setOnClickListener{
            when(onboarding_viewpager.currentItem){
                0 -> onboarding_viewpager.currentItem = 1
                1 -> onboarding_viewpager.currentItem = 2
                2 -> Unit
            }
        }

        get_started_btn.setOnClickListener{
            goToHomepage()
        }

        onbording_skip_btn.setOnClickListener{
            goToHomepage()
        }
    }

    override fun stopped() {}

    fun goToHomepage(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}