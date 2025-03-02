package com.example.my_shoppings.fragments.shopping

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.my_shoppings.R
import com.example.my_shoppings.databinding.FragmentHomeBinding
import com.example.my_shoppings.fragments.categories.AccessoryFragment
import com.example.my_shoppings.fragments.categories.CupboardFragment
import com.example.my_shoppings.fragments.categories.FurnitureFragment
import com.example.my_shoppings.fragments.categories.MainCategoryFragment
import com.example.my_shoppings.fragments.categories.TableFragment

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val titlesCategory = listOf("Home", "Chair", "Cupboard", "Table", "Accessory", "Furniture")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        val categoryFragments = listOf(
            MainCategoryFragment(),
            CartFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )

    }

}