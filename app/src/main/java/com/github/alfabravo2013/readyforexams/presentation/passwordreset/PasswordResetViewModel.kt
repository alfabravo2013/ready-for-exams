package com.github.alfabravo2013.readyforexams.presentation.passwordreset

import androidx.lifecycle.ViewModel
import com.github.alfabravo2013.readyforexams.domain.passwordreset.PasswordResetUseCase

class PasswordResetViewModel(private val passwordResetUseCase: PasswordResetUseCase) : ViewModel() {

    sealed class OnEvent {
        object ShowProgress : OnEvent()
        object HideProgress : OnEvent()
        object NavigateToHomeScreen : OnEvent()
        object NavigateToSignupScreen : OnEvent()
        object NavigateToPasswordResetScreen : OnEvent()
        data class Error(val message : String) : OnEvent()
    }
}
