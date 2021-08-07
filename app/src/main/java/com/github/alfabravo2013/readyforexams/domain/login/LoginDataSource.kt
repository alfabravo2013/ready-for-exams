package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result

interface LoginDataSource {
    fun login(email: String, password: String): Result
    fun resetPassword(email: String): Result
    fun signUp(email: String, password: String): Result
}
