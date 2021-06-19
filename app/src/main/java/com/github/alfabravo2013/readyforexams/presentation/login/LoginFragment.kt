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

        viewModel.onEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { unhandledEvent ->
                when (unhandledEvent) {
                    is OnEvent.NavigateToHomeScreen -> navigateToHomeScreen()
                    is OnEvent.NavigateToSignupScreen -> navigateToSignupScreen()
                    is OnEvent.NavigateToPasswordResetScreen -> navigateToPasswordResetScreen()
                    is OnEvent.ShowProcessing -> showProgressBar()
                    is OnEvent.HideProcessing -> hideProcessBar()
                    is OnEvent.Error -> showError(unhandledEvent.messageId)
                }
            }
        }
    }

    private fun showError(messageId: Int) {
        Toast.makeText(context, context?.getString(messageId), Toast.LENGTH_SHORT).show()
    }

    private fun hideProcessBar() {
        binding.loginProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.loginProgressBar.visibility = View.VISIBLE
    }

    private fun navigateToPasswordResetScreen() {
        Toast.makeText(context, "Navigating to Password Reset", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToSignupScreen() {
        Toast.makeText(context, "Navigating to Signup", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHomeScreen() {
        Toast.makeText(context, "Navigating to Home", Toast.LENGTH_SHORT).show()
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