package com.example.my_shoppings.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_shoppings.model.User
import com.example.my_shoppings.util.RegisterFieldValidation
import com.example.my_shoppings.util.RegisterValidation
import com.example.my_shoppings.util.Response
import com.example.my_shoppings.util.validationCheckForEmail
import com.example.my_shoppings.util.validationCheckForPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel(){
    private var _register = MutableLiveData<Response<FirebaseUser>>()
    val register : LiveData<Response<FirebaseUser>>
        get() = _register
    private var _validation = MutableLiveData<RegisterFieldValidation>()
    val validation :LiveData<RegisterFieldValidation>
        get() = _validation

    fun createAccount(user : User , password : String){
        if(!checkValidation(user,password)){
            val registerField = RegisterFieldValidation(validationCheckForEmail(user.email), validationCheckForPassword(password))
            viewModelScope.launch {
                _validation.value = registerField
            }
        }
        viewModelScope.launch{
            _register.value = Response.Loading()
        }
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                it.user?.let {
                    _register.value = Response.Success(it)
                }
            }.addOnFailureListener {
                _register.value = Response.Error(it.message.toString())
            }
    }
    fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validationCheckForEmail(user.email)
        val passwordValidation = validationCheckForPassword(password)
        val shouldRegister = emailValidation is RegisterValidation.sucess && passwordValidation is RegisterValidation.sucess
        return shouldRegister
    }
}