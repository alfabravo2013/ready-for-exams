package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result

class LocalDataSource {
    private val registeredUsers = mutableMapOf<String, String>()

    init {
        registeredUsers["test@test.com"] = "123456789"
    }

    fun signUp(email: String, password: String): Result {
        return if (registeredUsers.containsKey(email)) {
            Result.Failure("Invalid email")
        } else {
            registeredUsers[email] = password
            Result.Success
        }
    }

    fun resetPassword(email: String): Result {
        return if (registeredUsers.containsKey(email)) {
            val defaultPassword = "123456789"
            registeredUsers[email] = defaultPassword
            Result.Success
        } else {
            Result.Failure("Unknown email")
        }
    }

    fun login(email: String, password: String): Result {
        return if (registeredUsers.containsKey(email) && registeredUsers[email] == password) {
            Result.Success
        } else {
            Result.Failure("Invalid email or password")
        }
    }
}
