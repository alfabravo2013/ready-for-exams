package com.github.alfabravo2013.readyforexams

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.github.alfabravo2013.readyforexams.MainViewModel.OnEvent.HideBackButton
import com.github.alfabravo2013.readyforexams.MainViewModel.OnEvent.ShowBackButton
import com.github.alfabravo2013.readyforexams.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    private val onEventObserver = Observer<MainViewModel.OnEvent> { event ->
        when (event) {
            is ShowBackButton -> binding.activityMainToolbarGoBack.visibility = View.VISIBLE
            is HideBackButton -> binding.activityMainToolbarGoBack.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.onNavigationChanged(destination.id)
        }

        viewModel.onEvent.observe(this, onEventObserver)

        goBack()
    }

    fun setToolbarTitle(title: String = "") {
        binding.activityMainToolbarCustomTitle.text = title
    }

    private fun goBack() {
        binding.activityMainToolbarGoBack.setOnClickListener {
            onBackPressed()
        }
    }
}
