package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result

class LoginRepository(
    private val loginLocalDataSource: LoginLocalDataSource,
    private val loginRemoteDataSource: LoginRemoteDataSource
) {

    suspend fun login(email: String, password: String): Result =
        loginRemoteDataSource.login(email, password)

    suspend fun resetPassword(email: String): Result =
        loginRemoteDataSource.resetPassword(email)

    suspend fun signup(email: String, password: String): Result =
        loginRemoteDataSource.signup(email, password)

    suspend fun logout() = loginRemoteDataSource.logout()

    suspend fun isLoggedIn() = loginRemoteDataSource.isLoggedIn()
}
