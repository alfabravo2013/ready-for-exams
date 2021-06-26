package com.github.alfabravo2013.readyforexams.domain.login

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.github.alfabravo2013.readyforexams.util.Result

class LocalDataSource {
    private val registeredUsers = mutableMapOf<String, String>()

    init {
        registeredUsers["test@test.com"] = "123456789"
    }

    suspend fun signUp(email: String, password: String): Result =
        withContext(Dispatchers.IO) {
            if (registeredUsers.containsKey(email)) {
                Result.Failure
            } else {
                registeredUsers[email] = password
                Result.Success
            }
        }

    suspend fun resetPassword(email: String): Result =
        withContext(Dispatchers.IO) {
            if (registeredUsers.containsKey(email)) {
                val defaultPassword = "123456789"
                registeredUsers[email] = defaultPassword
                Result.Success
            } else {
                Result.Failure
            }
        }

    suspend fun login(email: String, password: String): Result =
        withContext(Dispatchers.IO) {
            if (registeredUsers.containsKey(email) && registeredUsers[email] == password) {
                Result.Success
            } else {
                Result.Failure
            }
        }
}
