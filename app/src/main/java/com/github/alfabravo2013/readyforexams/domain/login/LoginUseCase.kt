package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.isInvalidEmail
import com.github.alfabravo2013.readyforexams.util.isInvalidPassword

class LoginUseCase(private val repository: LoginRepository) {

    fun login(email: String, password: String): Result {
        if (email.isInvalidEmail()) {
            return Result.Failure("Invalid email")
        }

        if (password.isInvalidPassword()) {
            return Result.Failure("Invalid password")
        }

        return repository.login(email, password)
    }
}
