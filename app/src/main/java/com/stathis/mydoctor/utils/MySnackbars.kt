package com.stathis.mydoctor.utils

import android.view.View
import de.mateware.snacky.Snacky

class MySnackbars {

    fun successSnack(view : View, text : String){
        Snacky.builder().setView(view).success().setText(text).show()
    }

    fun errorSnack(view : View, text : String){
        Snacky.builder().setView(view).error().setText(text).show()
    }

    fun infoSnack(view : View, text : String){
        Snacky.builder().setView(view).info().setText(text).show()
    }
}