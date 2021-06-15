package com.github.alfabravo2013.readyforexams.presentation.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentLandingBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels()

    private val binding: FragmentLandingBinding by lazy { FragmentLandingBinding.bind(requireView()) }

}