package com.github.alfabravo2013.readyforexams.presentation.passwordreset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.passwordreset.PasswordResetUseCase
import com.github.alfabravo2013.readyforexams.util.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PasswordResetViewModel(private val passwordResetUseCase: PasswordResetUseCase) : ViewModel() {

    private val _onEvent = MutableLiveData<OnEvent>()
    val onEvent: LiveData<OnEvent> get() = _onEvent

    fun onPasswordResetClicked(email: String) = viewModelScope.launch {
        _onEvent.value = OnEvent.ShowProgress
        when (val result = passwordResetUseCase.resetPassword(email)) {
            is Result.Success -> {
                _onEvent.value = OnEvent.ShowDefaultPassword
                delay(1000L)
                _onEvent.value = OnEvent.NavigateToLoginScreen
            }
            is Result.Failure -> {
                val message = result.errorMessage
                _onEvent.value = OnEvent.Error(message)
            }
        }
        _onEvent.value = OnEvent.HideProgress
    }

    sealed class OnEvent {
        object ShowProgress : OnEvent()
        object HideProgress : OnEvent()
        object NavigateToLoginScreen : OnEvent()
        object ShowDefaultPassword: OnEvent()
        data class Error(val message : String) : OnEvent()
    }
}
