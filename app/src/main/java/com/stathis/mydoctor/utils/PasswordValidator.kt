package com.stathis.mydoctor.utils


import java.util.regex.Pattern

object PasswordValidator {

    fun validatePassword(password : String): Boolean {
        return when(password.length > 8){
            true -> {
                val letter = Pattern.compile("[a-zA-z]")
                val digit = Pattern.compile("[0-9]")
                val special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]")

                val hasLetter = letter.matcher(password)
                val hasDigit = digit.matcher(password)
                val hasSpecial = special.matcher(password)

                hasLetter.find() && hasDigit.find() && hasSpecial.find()
            }
            else -> false
        }
    }
}