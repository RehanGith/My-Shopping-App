package com.example.my_shoppings.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.my_shoppings.R
import com.example.my_shoppings.databinding.FragmentRegisterBinding
import com.example.my_shoppings.model.User
import com.example.my_shoppings.util.Response
import com.example.my_shoppings.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register){
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding.apply {
            Log.d("main", "register fragment")
            buttonRegisterRegister.setOnClickListener {
                val user = User(
                    edFirstNameRegister.text.toString(),
                    edLastNameRegister.text.toString(),
                    edEmailRegister.text.toString()
                )

                val password = edPasswordRegister.text.toString()
                if(user.firstName.isEmpty() || user.lastName.isEmpty() || user.email.isEmpty() || password.isEmpty()) {
                    Log.d("main", "empty fields")
                } else {
                    registerViewModel.createAccount(user, password)
                }
            }
        }



        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.register.collect { response ->
                    when(response) {
                        is Response.Error -> {
                            Log.d("main", response.message.toString())
                            binding.buttonRegisterRegister.revertAnimation()
                        }
                        is Response.Loading -> binding.buttonRegisterRegister.startAnimation()
                        is Response.Success -> {
                            Log.d("main", response.data.toString())
                            binding.buttonRegisterRegister.revertAnimation()
                        }

                        is Response.Unspecified -> Log.d("main", "unspecified")
                    }
                }
            }
        }

    }
}