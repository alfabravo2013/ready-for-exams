package com.github.alfabravo2013.readyforexams.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.util.Event
import com.github.alfabravo2013.readyforexams.util.isInvalidEmail
import com.github.alfabravo2013.readyforexams.util.isInvalidPassword
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private var emailAddress = ""
    private var password = ""

    private val _navigateToHomeScreen = MutableLiveData<Event<Boolean>>()
    val navigateToHomeScreen: LiveData<Event<Boolean>>
        get() = _navigateToHomeScreen

    private val _navigateToPasswordResetScreen = MutableLiveData<Event<Boolean>>()
    val navigateToPasswordResetScreen: LiveData<Event<Boolean>>
        get() = _navigateToPasswordResetScreen

    private val _navigateToSignupScreen = MutableLiveData<Event<Boolean>>()
    val navigateToSignupScreen: LiveData<Event<Boolean>>
        get() = _navigateToSignupScreen

    private val _errorLoginFailed = MutableLiveData<Event<Int>>()
    val errorLoginFailed: LiveData<Event<Int>>
        get() = _errorLoginFailed

    private val _showProgressBar = MutableLiveData<Boolean>().apply { value = false }
    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

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

        _showProgressBar.value = true
        delay(1000)

        if (emailAddress == mockEmailAddress && password == mockPassword) {
            _navigateToHomeScreen.value = Event(true)
        } else {
            val messageResource = R.string.login_login_failed_error_text
            _errorLoginFailed.value = Event(messageResource)
        }

        _showProgressBar.value = false
    }

    private fun showInvalidPasswordError() {
        val messageResource = R.string.login_invalid_password_error_text
        _errorLoginFailed.value = Event(messageResource)
    }

    private fun showInvalidEmailError() {
        val messageResource = R.string.login_invalid_email_error_text
        _errorLoginFailed.value = Event(messageResource)
    }

    fun onForgotPasswordLinkClick() {
        _navigateToPasswordResetScreen.value = Event(true)
    }

    fun onSignupLinkClick() {
        _navigateToSignupScreen.value = Event(true)
    }
}