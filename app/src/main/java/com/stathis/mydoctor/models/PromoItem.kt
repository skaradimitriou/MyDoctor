package com.stathis.mydoctor.models

import com.stathis.mydoctor.abstraction.LocalModel

data class PromoItem(

    val promo_text : String,
    val promo_desc : String,
    val promo_img : Int,

) : LocalModel {
    override fun equals(data: LocalModel): Boolean = false
}
