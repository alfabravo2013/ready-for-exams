package com.github.alfabravo2013.readyforexams.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.domain.login.LoginUseCase
import com.github.alfabravo2013.readyforexams.repository.AuthenticationResult
import com.github.alfabravo2013.readyforexams.repository.LoginRepositoryImpl
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import com.github.alfabravo2013.readyforexams.util.isInvalidEmail
import com.github.alfabravo2013.readyforexams.util.isInvalidPassword
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    fun onLoginButtonClick(email: String, password: String) = viewModelScope.launch {
        _onEvent.value = OnEvent.ShowProgress
        _onEvent.value = loginUseCase.login(email, password)
        _onEvent.value = OnEvent.HideProgress
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
