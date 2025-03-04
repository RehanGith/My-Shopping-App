package com.example.my_shoppings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_shoppings.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private var _login = MutableSharedFlow<Response<FirebaseUser>>()
    val login = _login.asSharedFlow()

    private var _resetPassword = MutableSharedFlow<Response<String>>()
    val resetPassword = _resetPassword.asSharedFlow()

    fun loginUser(name : String, password : String) {
        viewModelScope.launch {
            _login.emit(Response.Loading())
        }
        if (name.isEmpty() || password.isEmpty()) {
            viewModelScope.launch {
                _login.emit(Response.Error("Empty fields are not allowed"))
            }
        }
        firebaseAuth.signInWithEmailAndPassword(name, password)
            .addOnSuccessListener {
                viewModelScope.launch{
                    it.user?.apply {
                        _login.emit(Response.Success(this))
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _login.emit(Response.Error(it.message.toString()))
                }
            }
    }
    fun resetPassword(email: String) {
        viewModelScope.launch {
            _resetPassword.emit(Response.Loading())
        }
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _resetPassword.emit(Response.Success(email))
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _resetPassword.emit(Response.Error(it.message.toString()))
                }
            }
    }
}