package com.example.my_shoppings.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_shoppings.R
import com.example.my_shoppings.util.Constant
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class IntroductionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private var _navigation = MutableStateFlow(0)
    val navigation : StateFlow<Int> = _navigation
    companion object {
        const val  SHOPPING_FRAGMENT = 23
        val ACTION_OFTEN_FRAGMENT = R.id.action_introductionFragment_to_accountOptionFragment2
    }
    init {
        val isButtonClick = sharedPreferences.getBoolean(Constant.INTRODUCTION_EN, false)
        val user = firebaseAuth.currentUser

        if(user != null){
            viewModelScope.launch {
                _navigation.emit(SHOPPING_FRAGMENT)
            }
        } else if(isButtonClick){
            viewModelScope.launch {
                _navigation.emit(ACTION_OFTEN_FRAGMENT)
            }
        } else {
            Unit
        }
    }
    fun onButtonClick() {
        sharedPreferences.edit().putBoolean(Constant.INTRODUCTION_EN, true).apply()
    }
}