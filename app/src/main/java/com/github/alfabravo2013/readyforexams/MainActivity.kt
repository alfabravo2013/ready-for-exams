package com.github.alfabravo2013.readyforexams

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.github.alfabravo2013.readyforexams.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var onNavigateUpCallback: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityMainToolbar)
        supportActionBar?.title = ""
    }

    fun setToolbarTitle(title: String = "") {
        binding.activityMainToolbarCustomTitle.text = title
    }

    fun setToolbarUpButtonVisible(flag: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(flag)
    }

    fun setOnNavigateUpCallback(callback: (() -> Unit)?) {
        onNavigateUpCallback = callback
    }

    override fun onSupportNavigateUp(): Boolean {
        onNavigateUpCallback?.invoke() ?: return defaultNavigateUp()
        return true
    }

    private fun defaultNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp()
    }
}
