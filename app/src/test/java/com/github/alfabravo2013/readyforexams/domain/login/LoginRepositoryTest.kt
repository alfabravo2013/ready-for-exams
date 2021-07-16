package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class LoginRepositoryTest {
    private val localDataSource = spyk<LocalDataSource>()
    private val loginRepository = LoginRepository(localDataSource)

    private val registeredEmail = "test@test.com"
    private val unregisteredEmail = "unique@test.com"
    private val correctPassword = "123456789"
    private val incorrectPassword = "987654321"

    @Nested
    @DisplayName("When login")
    inner class LoginTests {

        @Test
        @DisplayName("Given registered email and correct password then Result.Success")
        fun loginWithExistingEmailAndCorrectPassword() {
            val actual = loginRepository.login(registeredEmail, correctPassword)

            assertTrue(actual is Result.Success)
            verify { localDataSource.login(registeredEmail, correctPassword) }
        }

        @Test
        @DisplayName("Given registered email and incorrect password Then Result.Failure")
        fun loginWithExistingEmailAndIncorrectPassword() {
            val actual = loginRepository.login(registeredEmail, incorrectPassword)

            assertTrue(actual is Result.Failure)
            verify { localDataSource.login(registeredEmail, incorrectPassword) }
        }

        @Test
        @DisplayName("Given unregistered email and correct password Then Result.Failure")
        fun loginWithNonExistingEmailAndCorrectPassword() {
            val actual = loginRepository.login(unregisteredEmail, correctPassword)

            assertTrue(actual is Result.Failure)
            verify { localDataSource.login(unregisteredEmail, correctPassword) }
        }

        @Test
        @DisplayName("Given unregistered email and incorrect password Then Result.Failure")
        fun loginWithNonExistingEmailAndIncorrectPassword() {
            val actual = loginRepository.login(unregisteredEmail, incorrectPassword)

            assertTrue(actual is Result.Failure)
            verify { localDataSource.login(unregisteredEmail, incorrectPassword) }
        }
    }

    @Nested
    @DisplayName("When signup")
    inner class SignUpTests {
        @Test
        @DisplayName("Given registered email and any password Then Result.Failure")
        fun signUpWithRegisteredEmail() {
            val actual = localDataSource.signUp(registeredEmail, correctPassword)

            assertTrue(actual is Result.Failure)
            verify { localDataSource.signUp(registeredEmail, correctPassword) }
        }

        @Test
        @DisplayName("Given unregistered email and any password Then Result.Success")
        fun signUpWithUnregisteredEmail() {
            val actual = localDataSource.signUp(unregisteredEmail, correctPassword)

            assertTrue(actual is Result.Success)
            verify { localDataSource.signUp(unregisteredEmail, correctPassword) }
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
            verify { localDataSource.resetPassword(registeredEmail) }
        }

        @Test
        @DisplayName("Given unregistered email Then Result.Failure")
        fun resetPasswordWithUnregisteredEmail() {
            val actual = localDataSource.resetPassword(unregisteredEmail)

            assertTrue(actual is Result.Failure)
            verify { localDataSource.resetPassword(unregisteredEmail) }
        }
    }
}
