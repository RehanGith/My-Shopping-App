package com.example.my_shoppings.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_shoppings.model.User
import com.example.my_shoppings.util.Constant
import com.example.my_shoppings.util.RegisterFieldValidation
import com.example.my_shoppings.util.RegisterValidation
import com.example.my_shoppings.util.Response
import com.example.my_shoppings.util.validationCheckForEmail
import com.example.my_shoppings.util.validationCheckForPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel(){
    private var _register = MutableLiveData<Response<User>>()
    val register : LiveData<Response<User>>
        get() = _register
    private var _validation = MutableLiveData<RegisterFieldValidation>()
    val validation :LiveData<RegisterFieldValidation>
        get() = _validation

    fun createAccount(user : User , password : String){
        if(!checkValidation(user,password)){
            val registerField = RegisterFieldValidation(validationCheckForEmail(user.email), validationCheckForPassword(password))
            _validation.value = registerField
        }
        viewModelScope.launch{
            _register.value = Response.Loading()
        }
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                it.user?.let {
                    saveUserInfo(it.uid,user)
                }
            }.addOnFailureListener {
                _register.value = Response.Error(it.message.toString())
            }
    }

    private fun saveUserInfo(userId: String, user: User) {
        db.collection(Constant.USER_COLLECTION)
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                _register.value = Response.Success(user)
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