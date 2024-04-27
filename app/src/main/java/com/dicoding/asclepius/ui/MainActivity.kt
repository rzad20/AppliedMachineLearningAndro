package com.dicoding.asclepius.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        // Set up custom navigation behavior based on menu item selection
        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.analyzeFragment -> {
                    // Navigate to the "Scan" destination
                    navController.navigate(R.id.analyzeFragment)
                    true
                }
                R.id.navigationArticle -> {
                    // Navigate to the "Article" destination 
                    navController.navigate(R.id.navigationArticle)
                    true
                }
                R.id.navigation_history -> {
                    // Navigate to the "History" destination
                    navController.navigate(R.id.navigation_history)
                    true
                }
                else -> false
            }
        }
    }
}
