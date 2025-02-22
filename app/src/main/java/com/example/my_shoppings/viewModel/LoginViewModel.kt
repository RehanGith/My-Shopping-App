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

    fun loginUser(name : String, password : String) {
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
}