package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

internal class LocalDataSourceTest {
    private val localDataSource = LocalDataSource()

    private val registeredEmail = "test@test.com"
    private val unregisteredEmail = "unique@test.com"
    private val correctPassword = "123456789"
    private val incorrectPassword = "987654321"

    @Nested
    @DisplayName("When signup")
    inner class SignUpTest {

        @Test
        @DisplayName("Given registered email Then Result.Failure")
        fun signUpWithRegisteredEmail() {
            val actual = localDataSource.signUp(registeredEmail, correctPassword)

            assertTrue(actual is Result.Failure)
        }

        @Test
        @DisplayName("Given unregistered email Then Result.Success")
        fun signUpWithUnregisteredEmail() {
            val actual = localDataSource.signUp(unregisteredEmail, correctPassword)

            assertTrue(actual is Result.Success)
        }
    }

    @Nested
    @DisplayName("When resetPassword")
    inner class ResetPasswordTest {

        @Test
        @DisplayName("Given registered email Then Result.Success")
        fun resetPasswordWithRegisteredEmail() {
            val actual = localDataSource.resetPassword(registeredEmail)

            assertTrue(actual is Result.Success)
        }

        @Test
        @DisplayName("Given unregistered email Then Result.Failure")
        fun resetPasswordWithUnregisteredEmail() {
            val actual = localDataSource.resetPassword(unregisteredEmail)

            assertTrue(actual is Result.Failure)
        }
    }

    @Nested
    @DisplayName("When login")
    inner class LoginTests {

        @Test
        @DisplayName("Given registered email and correct password Then Result.Success")
        fun loginWithRegisteredEmailAndCorrectPassword() {
            val actual = localDataSource.login(registeredEmail, correctPassword)

            assertTrue(actual is Result.Success)
        }

        @Test
        @DisplayName("Given registered email and incorrect password Then Result.Failure")
        fun loginWithRegisteredEmailAndIncorrectPassword() {
            val actual = localDataSource.login(registeredEmail, incorrectPassword)

            assertTrue(actual is Result.Failure)
        }

        @Test
        @DisplayName("Given unregistered email and correct password Then Result.Failure")
        fun loginWithUnregisteredEmailAndCorrectPassword() {
            val actual = localDataSource.login(unregisteredEmail, correctPassword)

            assertTrue(actual is Result.Failure)
        }

        @Test
        @DisplayName("Given unregistered email and incorrect password Then Result.Failure")
        fun loginWithUnregisteredEmailAndIncorrectPassword() {
            val actual = localDataSource.login(unregisteredEmail, incorrectPassword)

            assertTrue(actual is Result.Failure)
        }
    }
}
