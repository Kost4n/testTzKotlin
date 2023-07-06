package com.kost4n.testandroidapp

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kost4n.testandroidapp.R
import com.kost4n.testandroidapp.databinding.ActivityRoofBinding

class RoofActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoofBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoofBinding.inflate(layoutInflater)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(binding.root)

        val navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_roof_main)

        navView.setupWithNavController(navController)
    }
}