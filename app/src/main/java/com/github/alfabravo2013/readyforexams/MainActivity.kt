package com.github.alfabravo2013.readyforexams

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.alfabravo2013.readyforexams.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setToolbarTitle(title: String = "") {
        binding.activityMainToolbarCustomTitle.text = title
    }
}
