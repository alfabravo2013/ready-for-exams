package com.github.alfabravo2013.readyforexams.repository

interface LoginRepository {
    suspend fun signUp(email: String, password: String) : AuthenticationResult
    suspend fun resetPassword(email: String) : AuthenticationResult
    suspend fun singIn(email: String, password: String) : AuthenticationResult
}

sealed class AuthenticationResult {
    data class Success(val message: String = "") : AuthenticationResult()
    data class Failure(val message: String = "") : AuthenticationResult()
}
