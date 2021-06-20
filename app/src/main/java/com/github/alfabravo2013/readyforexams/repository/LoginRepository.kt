package com.github.alfabravo2013.readyforexams.repository

interface LoginRepository {
    fun signUp(email: String, password: String) : AuthenticationResult
    fun resetPassword(email: String) : AuthenticationResult
    fun singIn(email: String, password: String) : AuthenticationResult
}

sealed class AuthenticationResult {
    data class Success(val message: String = "") : AuthenticationResult()
    data class Failure(val message: String = "") : AuthenticationResult()
}