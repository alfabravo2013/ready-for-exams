package com.github.alfabravo2013.readyforexams.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentLoginBinding
import com.github.alfabravo2013.readyforexams.presentation.ToolbarFragment

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels()
    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUpToolbar()

        binding.loginLoginButton.setOnClickListener {
            val enteredEmailAddress = binding.loginEmailEditText.text.toString()
            val enteredPassword = binding.loginPasswordEditText.text.toString()
            viewModel.setEmailAddress(enteredEmailAddress)
            viewModel.setPassword(enteredPassword)
            viewModel.onLoginButtonClick()
        }

        binding.loginForgotPasswordLink.setOnClickListener {
            viewModel.onForgotPasswordLinkClick()
        }

        binding.loginSignupLink.setOnClickListener {
            viewModel.onSignupLinkClick()
        }

        viewModel.errorLoginFailed.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(context, context?.getString(it), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.navigateToHomeScreen.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    Toast.makeText(context, "Navigating to Home", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.navigateToSignupScreen.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    Toast.makeText(context, "Navigating to Signup", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.navigateToPasswordResetScreen.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    Toast.makeText(context, "Navigating to Password Reset", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        viewModel.showProgressBar.observe(viewLifecycleOwner) {
            binding.loginProgressBar.visibility = if (it == true) View.VISIBLE else View.GONE
        }
    }

    private fun setUpToolbar() {
        val toolbar = ToolbarFragment.getInstance(
            requireContext().getString(R.string.login_screen_title)
        )
        childFragmentManager
            .beginTransaction()
            .add(R.id.login_fragment_container, toolbar)
            .commit()
    }

    override fun onDestroyView() {
        val toolbar = childFragmentManager.findFragmentById(R.id.toolbar_container)
        toolbar?.let {
            childFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
        }
        super.onDestroyView()
    }
}