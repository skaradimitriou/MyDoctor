package com.stathis.mydoctor.abstraction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class AbstractActivity(layoutId : Int) : AppCompatActivity(layoutId) {

    abstract fun init()
    abstract fun running()
    abstract fun stopped()

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        init()
    }

    override fun onPostResume() {
        super.onPostResume()
        running()
    }

    override fun onStop() {
        stopped()
        super.onStop()
    }
}