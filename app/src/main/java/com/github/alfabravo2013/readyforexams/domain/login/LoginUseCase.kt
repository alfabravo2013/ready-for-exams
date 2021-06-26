package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.presentation.login.LoginViewModel.OnEvent
import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.isInvalidEmail
import com.github.alfabravo2013.readyforexams.util.isInvalidPassword

class LoginUseCase(private val repository: LoginRepository) {

    suspend fun login(email: String, password: String): OnEvent {
        if (email.isInvalidEmail()) {
            return OnEvent.Error(R.string.login_invalid_email_error_text)
        }

        if (password.isInvalidPassword()) {
            return OnEvent.Error(R.string.login_invalid_password_error_text)
        }

        return if (repository.login(email, password) is Result.Success) {
            OnEvent.NavigateToHomeScreen
        } else {
            OnEvent.Error(R.string.login_login_failed_error_text)
        }
    }
}
