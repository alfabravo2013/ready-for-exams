package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result

class LoginRepository(private val localDataSource: LocalDataSource) {

    fun login(email: String, password: String): Result = localDataSource.login(email, password)

    fun resetPassword(email: String): Result = localDataSource.resetPassword(email)

    fun signUp(email: String, password: String): Result = localDataSource.signUp(email, password)
}
