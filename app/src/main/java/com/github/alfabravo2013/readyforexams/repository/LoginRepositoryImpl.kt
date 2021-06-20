package com.github.alfabravo2013.readyforexams.repository

object LoginRepositoryImpl : LoginRepository {
    private val registeredUsers = mutableMapOf<String, String>()

    init {
        registeredUsers["test@test.com"] = "123456789"
    }

    override fun signUp(email: String, password: String): AuthenticationResult {
        return if (registeredUsers.containsKey(email)) {
            AuthenticationResult.Failure("Such email already exists")
        } else {
            registeredUsers[email] = password
            return AuthenticationResult.Success()
        }
    }

    override fun resetPassword(email: String): AuthenticationResult {
        return if (registeredUsers.containsKey(email)) {
            val defaultPassword = "123456789"
            registeredUsers[email] = defaultPassword
            AuthenticationResult.Success(defaultPassword)
        } else {
            AuthenticationResult.Failure()
        }
    }

    override fun singIn(email: String, password: String): AuthenticationResult {
        return if (registeredUsers.containsKey(email) && registeredUsers[email] == password) {
            AuthenticationResult.Success()
        } else {
            AuthenticationResult.Failure("You specified wrong email or password")
        }
    }
}