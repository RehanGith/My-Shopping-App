package com.example.my_shoppings.viewModel

import androidx.lifecycle.ViewModel
import com.example.my_shoppings.model.User
import com.example.my_shoppings.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel(){
    private var _register = MutableStateFlow<Response<FirebaseUser>>(Response.Unspecified())
    val register : Flow<Response<FirebaseUser>>
        get() = _register

    fun createAccount(user : User , password : String){
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                it.user?.let {
                    _register.value = Response.Success(it)
                }
            }.addOnFailureListener {
                _register.value = Response.Error(it.message.toString())
            }
    }
}