package com.stathis.mydoctor.features.onboarding.model

import com.stathis.mydoctor.abstraction.LocalModel

data class OnboardingItem(

    val img : Int,
    val header : String,
    val desc : String

) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
