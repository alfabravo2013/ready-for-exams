package com.github.alfabravo2013.readyforexams.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.repository.AuthenticationResult
import com.github.alfabravo2013.readyforexams.repository.LoginRepositoryImpl
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import com.github.alfabravo2013.readyforexams.util.isInvalidEmail
import com.github.alfabravo2013.readyforexams.util.isInvalidPassword
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    private val loginRepository = LoginRepositoryImpl

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    var signUpSuccess = false
        private set

    fun onSignupButtonClick(email: String, password: String) {
        when {
            email.isInvalidEmail() -> showInvalidEmailError()
            password.isInvalidPassword() -> showInvalidPasswordError()
            else -> registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) = viewModelScope.launch {
        _onEvent.value = OnEvent.ShowProgress

        if (loginRepository.signUp(email, password) is AuthenticationResult.Success) {
            signUpSuccess = true
            _onEvent.value = OnEvent.SignupSuccess
        } else {
            val messageResource = R.string.signup_registration_failed_error_text
            _onEvent.value = OnEvent.Error(messageResource)
        }

        _onEvent.value = OnEvent.HideProgress
    }

    private fun showInvalidPasswordError() {
        val messageResource = R.string.signup_invalid_password_error_text
        _onEvent.value = OnEvent.Error(messageResource)
    }

    private fun showInvalidEmailError() {
        val messageResource = R.string.signup_invalid_email_error_text
        _onEvent.value = OnEvent.Error(messageResource)
    }

    fun onSuccessDialogBackButtonClick() {
        _onEvent.value = OnEvent.NavigateToLoginScreen
    }

    sealed class OnEvent {
        object ShowProgress : OnEvent()
        object HideProgress : OnEvent()
        object SignupSuccess : OnEvent()
        object NavigateToLoginScreen : OnEvent()
        data class Error(val messageId: Int) : OnEvent()
    }
}