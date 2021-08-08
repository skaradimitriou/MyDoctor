package com.stathis.mydoctor.abstraction

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class AbstractAndroidViewModel(app : Application) : AndroidViewModel(app) {

    fun getString(resId: Int) = getApplication<Application>().getString(resId)
}