package com.github.alfabravo2013.readyforexams.presentation.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentLandingBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class LandingFragment : BaseFragment(R.layout.fragment_landing) {
    private val viewModel: LandingViewModel by viewModels()

    private lateinit var binding: FragmentLandingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLandingBinding.bind(view)
        disableToolbar()

        val adapter = ViewPagerAdapter(this)
        binding.landingViewPager.adapter = adapter
        TabLayoutMediator(binding.landingTabLayout, binding.landingViewPager) { _, _ -> }.attach()

        binding.landingLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_loginFragment)
        }
    }
}

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        private const val TOTAL_PAGES = 3
    }

    override fun getItemCount(): Int = TOTAL_PAGES

    override fun createFragment(position: Int): Fragment = FirstOnboardingFragment()
}