package com.example.my_shoppings.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.my_shoppings.R
import com.example.my_shoppings.app.ShoppingActivity
import com.example.my_shoppings.databinding.FragmentIntroductionBinding
import com.example.my_shoppings.viewModel.IntroductionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroductionFragment: Fragment(R.layout.fragment_introduction) {
    private lateinit var binding: FragmentIntroductionBinding
    private val viewModel by viewModels<IntroductionViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIntroductionBinding.bind(view)

        binding.btnStart.setOnClickListener {
            viewModel.onButtonClick()
            findNavController().navigate(R.id.action_introductionFragment_to_accountOptionFragment2)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigation.collect {
                    when(it) {
                        IntroductionViewModel.SHOPPING_FRAGMENT -> {
                            val intent =
                                Intent(requireContext(), ShoppingActivity::class.java).apply {
                                    // These flags clear the existing task and start a new one
                                    flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                            startActivity(intent)
                            requireActivity().finish()
                        }
                        IntroductionViewModel.ACTION_OFTEN_FRAGMENT -> {
                            findNavController().navigate(it)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}