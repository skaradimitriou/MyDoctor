package com.stathis.mydoctor.features.onboarding

import androidx.lifecycle.ViewModel
import com.stathis.mydoctor.R
import com.stathis.mydoctor.features.onboarding.adapter.OnboardingAdapter
import com.stathis.mydoctor.features.onboarding.model.OnboardingItem

class OnboardingViewModel : ViewModel() {

    val adapter by lazy { OnboardingAdapter() }

    init {
        createOnboardingItems()
    }

    fun createOnboardingItems() {
        adapter.submitList(
            listOf(
                OnboardingItem(
                    R.drawable.ic_stethoscope,
                    "Onboarding title 1",
                    "Onboarding desc 1"
                ),
                OnboardingItem(
                    R.drawable.ic_ophthalmologist,
                    "Onboarding title 2",
                    "Onboarding desc 2"
                ),
                OnboardingItem(
                    R.drawable.ic_dermatologist,
                    "Onboarding title 3",
                    "Onboarding desc 3"
                )
            )
        )
    }
}