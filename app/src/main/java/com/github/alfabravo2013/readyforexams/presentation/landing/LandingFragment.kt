package com.github.alfabravo2013.readyforexams.presentation.landing

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentLandingBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class LandingFragment : BaseFragment() {
    private val viewModel: LandingViewModel by viewModel()

    private var _binding: FragmentLandingBinding? = null
    private val binding: FragmentLandingBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle()

        val adapter = ViewPagerAdapter(this)
        binding.landingViewPager.adapter = adapter
        TabLayoutMediator(binding.landingTabLayout, binding.landingViewPager) { _, _ -> }.attach()

        binding.landingLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        private const val TOTAL_PAGES = 3
    }

    override fun getItemCount(): Int = TOTAL_PAGES

    override fun createFragment(position: Int): Fragment = FirstOnboardingFragment()
}
