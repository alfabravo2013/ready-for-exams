package com.github.alfabravo2013.readyforexams.domain.passwordreset

import com.github.alfabravo2013.readyforexams.domain.login.LoginRepository
import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.isInvalidEmail

class PasswordResetUseCase(private val loginRepository: LoginRepository) {

    suspend fun resetPassword(email: String): Result {
        if (email.isInvalidEmail()) {
            return Result.Failure("Invalid email")
        }

        return loginRepository.resetPassword(email)
    }
}
