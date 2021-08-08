package com.github.alfabravo2013.readyforexams.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.login.LoginUseCase
import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    fun onLoginButtonClick(email: String, password: String) = viewModelScope.launch {
        _onEvent.value = OnEvent.ShowProgress

        when (val result = loginUseCase.login(email, password)) {
            is Result.Success -> _onEvent.value = OnEvent.NavigateToHomeScreen
            is Result.Failure -> {
                val message = result.errorMessage
                _onEvent.value = OnEvent.Error(message)
            }
        }
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
        data class Error(val message: String) : OnEvent()
    }
}
