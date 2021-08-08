package com.github.alfabravo2013.readyforexams.networking

import com.github.alfabravo2013.readyforexams.util.Result

interface PasswordAuthService {
    suspend fun login(email: String, password: String): Result
    suspend fun logout()
    suspend fun signup(email: String, password: String): Result
    suspend fun resetPassword(email: String): Result
    suspend fun isLoggedIn(): Boolean
}
