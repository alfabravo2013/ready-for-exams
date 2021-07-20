package com.github.alfabravo2013.readyforexams.presentation.passwordreset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentPasswordResetBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.github.alfabravo2013.readyforexams.presentation.passwordreset.PasswordResetViewModel.OnEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordResetFragment : BaseFragment() {
    private val viewModel: PasswordResetViewModel by viewModel()

    private var _binding: FragmentPasswordResetBinding? = null
    private val binding: FragmentPasswordResetBinding get() = _binding!!

    private val onEventObserver = Observer<OnEvent> { event ->
        when (event) {
            is OnEvent.ShowProgress -> binding.passwordResetProgressBar.visibility = View.VISIBLE
            is OnEvent.HideProgress -> binding.passwordResetProgressBar.visibility = View.GONE
            is OnEvent.ShowDefaultPassword -> showDefaultPassword()
            is OnEvent.NavigateToLoginScreen -> navigateToLoginScreen()
            is OnEvent.Error -> showError(event.message)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordResetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle(requireContext().getString(R.string.password_reset_header_text))

        viewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)

        binding.passwordResetSignupButton.setOnClickListener {
            val enteredEmail = binding.passwordResetEmailEditText.text.toString()
            viewModel.onPasswordResetClicked(enteredEmail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showDefaultPassword() {
        val context = requireContext()
        val message = context.getString(R.string.password_reset_success_text)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToLoginScreen() {
        findNavController().popBackStack()
    }
}
