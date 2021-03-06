package com.github.alfabravo2013.readyforexams.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentLoginBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.github.alfabravo2013.readyforexams.presentation.login.LoginViewModel.OnEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by viewModel()

    private val onEventObserver = Observer<OnEvent> { event ->
        when (event) {
            is OnEvent.NavigateToHomeScreen -> navigateToHomeScreen()
            is OnEvent.NavigateToSignupScreen -> navigateToSignupScreen()
            is OnEvent.NavigateToPasswordResetScreen -> navigateToPasswordResetScreen()
            is OnEvent.ShowProgress -> binding.loginProgressBar.visibility = View.VISIBLE
            is OnEvent.HideProgress -> binding.loginProgressBar.visibility = View.GONE
            is OnEvent.Error -> showError(event.message)
        }
    }

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        binding.loginLoginButton.setOnClickListener {
            val enteredEmail = binding.loginEmailEditText.text.toString()
            val enteredPassword = binding.loginPasswordEditText.text.toString()
            viewModel.onLoginButtonClick(enteredEmail, enteredPassword)
        }

        binding.loginForgotPasswordLink.setOnClickListener {
            viewModel.onForgotPasswordLinkClick()
        }

        binding.loginSignupLink.setOnClickListener {
            viewModel.onSignupLinkClick()
        }

        viewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)
    }

    private fun setTitle() {
        val title = requireContext().getString(R.string.login_screen_title)
        setToolbarTitle(title)
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToPasswordResetScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_passwordResetFragment)
    }

    private fun navigateToSignupScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
}
