package com.github.alfabravo2013.readyforexams.domain.signup

import com.github.alfabravo2013.readyforexams.domain.login.LoginRepository
import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.isInvalidEmail
import com.github.alfabravo2013.readyforexams.util.isInvalidPassword

class SignupUseCase(private val loginRepository: LoginRepository) {

    fun signup(email: String, password: String, confirmedPassword: String): Result {
        if (email.isInvalidEmail()) {
            return Result.Failure("Invalid email")
        }

        if (password.isInvalidPassword()) {
            return Result.Failure("Invalid password")
        }

        if (password != confirmedPassword) {
            return Result.Failure("The password does not match the confirmed password")
        }

        return loginRepository.signUp(email, password)
    }
}
