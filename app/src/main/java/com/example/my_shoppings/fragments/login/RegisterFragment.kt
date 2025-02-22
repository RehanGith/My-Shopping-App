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
import kotlinx.coroutines.launch

class RegisterFragment : Fragment(R.layout.fragment_register){
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding.apply {
            buttonRegisterRegister.setOnClickListener {
                val user = User(
                    edFirstNameRegister.text.toString(),
                    edLastNameRegister.text.toString(),
                    edEmailRegister.text.toString()
                )
                val password = edPasswordRegister.text.toString()
                registerViewModel.createAccount(user, password)

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
                    }
                }
            }
        }

    }
}