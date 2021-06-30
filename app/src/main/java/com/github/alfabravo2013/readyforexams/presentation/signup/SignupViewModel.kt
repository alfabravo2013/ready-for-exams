package com.github.alfabravo2013.readyforexams.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.signup.SignupUseCase
import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import kotlinx.coroutines.launch

class SignupViewModel(private val signupUseCase: SignupUseCase) : ViewModel() {

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    private var isSignUpSuccessful = false

    fun onSignupButtonClick(email: String, password: String, confirmedPassword: String) {
        viewModelScope.launch {
            _onEvent.value = OnEvent.ShowProgress
            when (val result = signupUseCase.signup(email, password, confirmedPassword)) {
                is Result.Success -> {
                    isSignUpSuccessful = true
                    _onEvent.value = OnEvent.SignupSuccess
                }
                is Result.Failure -> {
                    _onEvent.value = OnEvent.Error(result.errorMessage)
                }
            }
            _onEvent.value = OnEvent.HideProgress
        }
    }

    fun onSuccessDialogBackButtonClick() {
        _onEvent.value = OnEvent.NavigateToLoginScreen
    }

    fun checkSignupStatus() {
        if (isSignUpSuccessful) {
            _onEvent.value = OnEvent.SignupSuccess
        }
    }

    sealed class OnEvent {
        object ShowProgress : OnEvent()
        object HideProgress : OnEvent()
        object SignupSuccess : OnEvent()
        object NavigateToLoginScreen : OnEvent()
        data class Error(val message: String) : OnEvent()
    }
}
