package com.example.my_shoppings.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.my_shoppings.R
import com.example.my_shoppings.databinding.ActivityShoppingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setupWithNavController(navController)

    }
}