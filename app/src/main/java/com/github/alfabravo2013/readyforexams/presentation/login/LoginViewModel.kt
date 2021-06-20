package com.github.alfabravo2013.readyforexams.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import com.github.alfabravo2013.readyforexams.util.isInvalidEmail
import com.github.alfabravo2013.readyforexams.util.isInvalidPassword
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private var emailAddress = ""
    private var password = ""

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    fun setEmailAddress(emailAddress: String) {
        this.emailAddress = emailAddress
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun onLoginButtonClick() {
        when {
            emailAddress.isInvalidEmail() -> showInvalidEmailError()
            password.isInvalidPassword() -> showInvalidPasswordError()
            else -> authenticateUser()
        }
    }

    private fun authenticateUser() = viewModelScope.launch {
        val mockEmailAddress = "test@test.com"
        val mockPassword = "123456789"

        _onEvent.value = OnEvent.ShowProgress
        delay(1000)

        if (emailAddress == mockEmailAddress && password == mockPassword) {
            _onEvent.value = OnEvent.NavigateToHomeScreen
        } else {
            val messageResource = R.string.login_login_failed_error_text
            _onEvent.value = OnEvent.Error(messageResource)
        }

        _onEvent.value = OnEvent.HideProgress
    }

    private fun showInvalidPasswordError() {
        val messageResource = R.string.login_invalid_password_error_text
        _onEvent.value = OnEvent.Error(messageResource)
    }

    private fun showInvalidEmailError() {
        val messageResource = R.string.login_invalid_email_error_text
        _onEvent.value = OnEvent.Error(messageResource)
    }

    fun onForgotPasswordLinkClick() {
        _onEvent.value = OnEvent.NavigateToPasswordResetScreen
    }

    fun onSignupLinkClick() {
        _onEvent.value = OnEvent.NavigateToSignupScreen
    }

    sealed class OnEvent {
        object ShowProgress : OnEvent()
        object HideProgress : OnEvent()
        object NavigateToHomeScreen : OnEvent()
        object NavigateToSignupScreen : OnEvent()
        object NavigateToPasswordResetScreen : OnEvent()
        data class Error(val messageId : Int) : OnEvent()
    }
}