package com.stathis.mydoctor.abstraction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class AbstractFragment(layoutId : Int) : Fragment(layoutId) {

    abstract fun init()
    abstract fun running()
    abstract fun stopped()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        running()
    }

    override fun onStop() {
        stopped()
        super.onStop()
    }
}