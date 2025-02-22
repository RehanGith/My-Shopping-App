package com.example.my_shoppings.util

import android.util.Patterns
import org.intellij.lang.annotations.Pattern

fun validationCheckForEmail(email: String): RegisterValidation {
    if(email.isEmpty()) {
        return RegisterValidation.failure("email cannot be empty")
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return RegisterValidation.failure("email is not valid")
    }
    return RegisterValidation.sucess
}
fun validationCheckForPassword(password: String): RegisterValidation {
    if(password.isEmpty()) {
        return RegisterValidation.failure("password cannot be empty")
    }
    if(password.length < 8) {
        return RegisterValidation.failure("password should contains 8 char")
    }
    return RegisterValidation.sucess
}
