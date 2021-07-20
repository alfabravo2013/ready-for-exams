package com.github.alfabravo2013.readyforexams.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentSignupBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.github.alfabravo2013.readyforexams.presentation.signup.SignupViewModel.OnEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : BaseFragment() {
    private val viewModel: SignupViewModel by viewModel()

    private var _binding: FragmentSignupBinding? = null
    private val binding: FragmentSignupBinding get() = _binding!!

    private val onEventObserver = Observer<OnEvent> { event ->
        when (event) {
            is OnEvent.NavigateToLoginScreen -> navigateToLoginScreen()
            is OnEvent.ShowProgress -> binding.signupProgressBar.visibility = View.VISIBLE
            is OnEvent.HideProgress -> binding.signupProgressBar.visibility = View.GONE
            is OnEvent.Error -> showError(event.message)
            is OnEvent.SignupSuccess -> showSignupSuccessDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbar()
        viewModel.checkSignupStatus()

        viewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)

        with(binding) {
            signupSignupButton.setOnClickListener {
                val enteredEmail = signupEmailEditText.text.toString()
                val enteredPassword = signupPasswordEditText.text.toString()
                val confirmedPassword = signupPasswordConfirmEditText.text.toString()
                viewModel.onSignupButtonClick(enteredEmail, enteredPassword, confirmedPassword)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setToolbar() {
        val title = requireContext().getString(R.string.signup_header_text)
        setToolbarTitle(title)
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
