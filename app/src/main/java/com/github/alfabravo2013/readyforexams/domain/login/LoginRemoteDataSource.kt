package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.networking.PasswordAuthService
import com.github.alfabravo2013.readyforexams.util.Result

class LoginRemoteDataSource(private val authService: PasswordAuthService) {

    suspend fun login(email: String, password: String): Result = authService.login(email, password)

    suspend fun resetPassword(email: String): Result = authService.resetPassword(email)

    suspend fun signup(email: String, password: String): Result =
        authService.signup(email, password)

    suspend fun isLoggedIn(): Boolean = authService.isLoggedIn()

    suspend fun logout() = authService.logout()
}
