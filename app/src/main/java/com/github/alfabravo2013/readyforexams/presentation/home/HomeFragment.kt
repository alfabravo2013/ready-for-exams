package com.github.alfabravo2013.readyforexams.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentHomeBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        setToolbarTitle(requireContext().getString(R.string.home_title_text))
    }
}