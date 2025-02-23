package com.example.my_shoppings.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.my_shoppings.R
import com.example.my_shoppings.databinding.FragmentRegisterBinding
import com.example.my_shoppings.model.User
import com.example.my_shoppings.util.RegisterValidation
import com.example.my_shoppings.util.Response
import com.example.my_shoppings.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding.apply {
            Log.d("main", "register fragment")
            buttonRegisterRegister.setOnClickListener {
                val user = User(
                    edFirstNameRegister.text.toString().trim(),
                    edLastNameRegister.text.toString().trim(),
                    edEmailRegister.text.toString().trim()
                )

                val password = edPasswordRegister.text.toString()
                if (user.firstName.isEmpty() || user.lastName.isEmpty() || user.email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill the fields", Toast.LENGTH_LONG).show()
                } else {
                    registerViewModel.createAccount(user, password)
                }
            }
        }
        binding.tvDoYouHaveAccount.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.registerFragment, true)
                .build()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment, null , navOptions)
        }
        registerViewModel.register.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Error -> {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                    binding.buttonRegisterRegister.revertAnimation()
                }

                is Response.Loading -> binding.buttonRegisterRegister.startAnimation()
                is Response.Success -> {
                    binding.buttonRegisterRegister.revertAnimation()

                }

                else -> Log.d("main", "unspecified")
            }
        }

        registerViewModel.validation.observe(viewLifecycleOwner) {validation->
            if(validation.email is RegisterValidation.failure){
                binding.edEmailRegister.apply {
                    requestFocus()
                    error = validation.email.msg
                }
            }
            if(validation.password is RegisterValidation.failure){
                binding.edPasswordRegister.apply {
                    requestFocus()
                    error = validation.password.msg
                }
            }
        }


    }
}