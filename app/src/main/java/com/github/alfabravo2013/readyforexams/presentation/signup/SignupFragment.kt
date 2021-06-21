package com.github.alfabravo2013.readyforexams.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentSignupBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment

class SignupFragment : BaseFragment(R.layout.fragment_signup) {
    private val viewModel: SignupViewModel by viewModels()
    private lateinit var binding: FragmentSignupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignupBinding.bind(view)
        setToolbarTitle(requireContext().getString(R.string.signup_header_text))
    }
}