package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.networking.PasswordAuthService
import com.github.alfabravo2013.readyforexams.util.Result

class LoginRemoteDataSource(private val authProvider: PasswordAuthService) {

    suspend fun login(email: String, password: String): Result = authProvider.login(email, password)

    suspend fun resetPassword(email: String): Result = authProvider.resetPassword(email)

    suspend fun signup(email: String, password: String): Result =
        authProvider.signup(email, password)

    suspend fun isLoggedIn(): Boolean = authProvider.isLoggedIn()

    suspend fun logout() = authProvider.logout()
}
