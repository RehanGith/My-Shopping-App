package com.example.my_shoppings.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.my_shoppings.R
import com.example.my_shoppings.databinding.FragmentLoginBinding
import com.example.my_shoppings.util.Response
import com.example.my_shoppings.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.apply {
            buttonLoginLogin.setOnClickListener {
                val email = edEmailLogin.text.toString().trim()
                val password = edPasswordLogin.text.toString()
                loginViewModel.loginUser(email, password)
            }
        }
        binding.tvDontHaveAccount.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment, null, navOptions)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.login.collect {
                    when(it) {
                        is Response.Error -> {
                            Log.e("LoginFragment", it.message.toString())
                            binding.buttonLoginLogin.revertAnimation()
                        }
                        is Response.Loading -> {
                            binding.buttonLoginLogin.startAnimation()
                        }
                        is Response.Success -> {
                            val navController = findNavController()
                            binding.buttonLoginLogin.revertAnimation()
                            val navOptions = NavOptions.Builder()
                                .setPopUpTo(navController.graph.startDestinationId, true)
                                .build()
                            findNavController().navigate(R.id.action_loginFragment_to_shoppingFragment, null, navOptions)
                        }
                    }
                }
            }
        }
    }
}