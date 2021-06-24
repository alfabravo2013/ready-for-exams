package com.github.alfabravo2013.readyforexams.presentation.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentSignupBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.github.alfabravo2013.readyforexams.presentation.signup.SignupViewModel.OnEvent
import org.koin.android.ext.android.inject

class SignupFragment : BaseFragment(R.layout.fragment_signup) {
    private val viewModel: SignupViewModel by inject()
    private lateinit var binding: FragmentSignupBinding

    private val onEventObserver = Observer<OnEvent> { event ->
        when (event) {
            is OnEvent.NavigateToLoginScreen -> navigateToLoginScreen()
            is OnEvent.ShowProgress -> binding.signupProgressBar.visibility = View.VISIBLE
            is OnEvent.HideProgress -> binding.signupProgressBar.visibility = View.GONE
            is OnEvent.Error -> showError(event.messageId)
            is OnEvent.SignupSuccess -> showSignupSuccessDialog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignupBinding.bind(view)

        setToolbar()
        viewModel.checkSignupStatus()

        viewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)

        binding.signupSignupButton.setOnClickListener {
            val enteredEmail = binding.signupEmailEditText.text.toString()
            val enteredPassword = binding.signupPasswordEditText.text.toString()
            viewModel.onSignupButtonClick(enteredEmail, enteredPassword)
        }
    }

    private fun setToolbar() {
        val title = requireContext().getString(R.string.signup_header_text)
        setToolbarTitle(title)
    }

    private fun showError(messageId: Int) {
        val context = requireContext()
        Toast.makeText(context, context.getString(messageId), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToLoginScreen() {
        findNavController().popBackStack()
    }

    private fun showSignupSuccessDialog() {
        with(binding) {
            signupFragmentContainer.visibility = View.GONE
            signupSuccessDialog.visibility = View.VISIBLE
            signupSuccessDialogBackButton.setOnClickListener {
                viewModel.onSuccessDialogBackButtonClick()
            }
        }
    }
}
