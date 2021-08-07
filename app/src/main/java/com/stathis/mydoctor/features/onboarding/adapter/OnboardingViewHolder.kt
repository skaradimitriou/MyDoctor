package com.stathis.mydoctor.features.onboarding.adapter

import android.view.View
import com.stathis.mydoctor.abstraction.AbstractViewHolder
import com.stathis.mydoctor.abstraction.LocalModel
import com.stathis.mydoctor.features.onboarding.model.OnboardingItem

class OnboardingViewHolder(itemView : View) : AbstractViewHolder(itemView) {

    override fun present(data: LocalModel) {
        when(data){
            is OnboardingItem -> {
                itemView.onboarding_img.setImageResource(data.img)
                itemView.onboarding_title.text = data.header
                itemView.onboarding_description.text = data.desc
            }
        }
    }
}