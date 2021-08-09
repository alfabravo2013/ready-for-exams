package com.github.alfabravo2013.readyforexams

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.github.alfabravo2013.readyforexams.MainViewModel.OnEvent.HideBackButton
import com.github.alfabravo2013.readyforexams.MainViewModel.OnEvent.ShowBackButton
import com.github.alfabravo2013.readyforexams.MainViewModel.OnEvent.ShowLogoutButton
import com.github.alfabravo2013.readyforexams.MainViewModel.OnEvent.HideLogoutButton
import com.github.alfabravo2013.readyforexams.MainViewModel.OnEvent.NavigateToLoginScreen
import com.github.alfabravo2013.readyforexams.MainViewModel.OnEvent.SetStartDestinationLogin
import com.github.alfabravo2013.readyforexams.MainViewModel.OnEvent.SetStartDestinationHome
import com.github.alfabravo2013.readyforexams.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    private val onEventObserver = Observer<MainViewModel.OnEvent> { event ->
        when (event) {
            is ShowBackButton -> binding.activityMainToolbarGoBack.visibility = View.VISIBLE
            is HideBackButton -> binding.activityMainToolbarGoBack.visibility = View.GONE
            is ShowLogoutButton -> binding.activityMainToolbarLogout.visibility = View.VISIBLE
            is HideLogoutButton -> binding.activityMainToolbarLogout.visibility = View.GONE
            is NavigateToLoginScreen -> navigateToLoginScreen()
            is SetStartDestinationHome -> setStartDestination(R.id.homeFragment)
            is SetStartDestinationLogin -> setStartDestination(R.id.loginFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.onEvent.observe(this, onEventObserver)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.onNavigationChanged(destination.id)
        }

        binding.activityMainToolbarLogout.setOnClickListener {
            viewModel.onLogout()
        }

        goBack()
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkUserStatus()
    }

    fun setToolbarTitle(title: String = "") {
        binding.activityMainToolbarCustomTitle.text = title
    }

    private fun goBack() {
        binding.activityMainToolbarGoBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setStartDestination(fragmentId: Int) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.landingFragment, true)
            .build()
        findNavController(R.id.nav_host).navigate(fragmentId, null, navOptions)
    }

    private fun navigateToLoginScreen() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.landingFragment, true)
            .build()
        findNavController(R.id.nav_host).navigate(R.id.loginFragment, null, navOptions)
    }
}
