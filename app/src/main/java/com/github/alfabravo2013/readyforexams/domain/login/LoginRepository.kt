package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result

class LoginRepository(
    private val loginLocalDataSource: LoginLocalDataSource,
    private val loginRemoteDataSource: LoginRemoteDataSource
) {

    fun login(email: String, password: String): Result = loginRemoteDataSource.login(email, password)

    fun resetPassword(email: String): Result = loginLocalDataSource.resetPassword(email)

    fun signUp(email: String, password: String): Result =
        loginLocalDataSource.signUp(email, password)
}
