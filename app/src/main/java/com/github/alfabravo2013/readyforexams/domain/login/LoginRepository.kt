package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result

class LoginRepository(private val localDataSource: LocalDataSource) {

    suspend fun login(email: String, password: String): Result =
        localDataSource.login(email, password)
}
