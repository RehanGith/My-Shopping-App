package com.example.my_shoppings.util

sealed class RegisterValidation {
    object sucess: RegisterValidation()
    class failure(val msg: String): RegisterValidation()
}

data class RegisterFieldValidation(
    val email: RegisterValidation,
    val password: RegisterValidation

)