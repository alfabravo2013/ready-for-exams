package com.github.alfabravo2013.readyforexams.presentation.passwordreset

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentPasswordResetBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment

class PasswordResetFragment : BaseFragment(R.layout.fragment_password_reset) {
    private val viewModel: PasswordResetViewModel by viewModels()
    private lateinit var binding: FragmentPasswordResetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPasswordResetBinding.bind(view)
        setToolbarTitle(requireContext().getString(R.string.password_reset_header_text))
    }
}
