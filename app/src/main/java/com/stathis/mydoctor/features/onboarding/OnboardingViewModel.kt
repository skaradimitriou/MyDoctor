package com.stathis.mydoctor.features.onboarding

import android.app.Application
import androidx.lifecycle.ViewModel
import com.stathis.mydoctor.R
import com.stathis.mydoctor.abstraction.AbstractAndroidViewModel
import com.stathis.mydoctor.features.onboarding.adapter.OnboardingAdapter
import com.stathis.mydoctor.features.onboarding.model.OnboardingItem

class OnboardingViewModel(app : Application) : AbstractAndroidViewModel(app) {

    val adapter by lazy { OnboardingAdapter() }

    init {
        createOnboardingItems()
    }

    fun createOnboardingItems() {
        adapter.submitList(
            listOf(
                OnboardingItem(
                    R.drawable.ic_doctor,
                    getString(R.string.onboarding_header_one),
                    getString(R.string.onboarding_desc_one)
                ),
                OnboardingItem(
                    R.drawable.ic_doctor,
                    getString(R.string.onboarding_header_two),
                    getString(R.string.onboarding_desc_two)
                ),
                OnboardingItem(
                    R.drawable.ic_calendar_onboarding,
                    getString(R.string.onboarding_header_three),
                    getString(R.string.onboarding_desc_three)
                )
            )
        )
    }
}