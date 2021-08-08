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
                    R.drawable.ic_doctor,
                    "Search for a doctor",
                    "Search for doctors by name from our search screen. Your latest searches will be shown there."
                ),
                OnboardingItem(
                    R.drawable.ic_doctor,
                    "Search doctors by category",
                    "Looking for a specific doctor category? We've got you covered! Just go to all categories and find what you're looking for."
                ),
                OnboardingItem(
                    R.drawable.ic_calendar_onboarding,
                    "Add a doctor appointment",
                    "Once you have found your doctor, you're able to add an appointment to your list."
                )
            )
        )
    }
}