package com.github.alfabravo2013.readyforexams.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

object LoginRepositoryImpl : LoginRepository {
    private const val MIN_DELAY = 35L
    private const val MAX_DELAY = 2500L

    private val registeredUsers = mutableMapOf<String, String>()

    init {
        registeredUsers["test@test.com"] = "123456789"
    }

    override suspend fun signUp(email: String, password: String): AuthenticationResult =
        withContext(Dispatchers.IO) {
            delay(getNetworkDelay())
            if (registeredUsers.containsKey(email)) {
                AuthenticationResult.Failure("Such email already exists")
            } else {
                registeredUsers[email] = password
                AuthenticationResult.Success()
            }
        }

    override suspend fun resetPassword(email: String): AuthenticationResult =
        withContext(Dispatchers.IO) {
            delay(getNetworkDelay())
            if (registeredUsers.containsKey(email)) {
                val defaultPassword = "123456789"
                registeredUsers[email] = defaultPassword
                AuthenticationResult.Success(defaultPassword)
            } else {
                AuthenticationResult.Failure()
            }
        }

    override suspend fun singIn(email: String, password: String): AuthenticationResult =
        withContext(Dispatchers.IO) {
            delay(getNetworkDelay())
            if (registeredUsers.containsKey(email) && registeredUsers[email] == password) {
                AuthenticationResult.Success()
            } else {
                AuthenticationResult.Failure("You specified wrong email or password")
            }
        }

    private fun getNetworkDelay(): Long = Random.nextLong(MIN_DELAY, MAX_DELAY)
}