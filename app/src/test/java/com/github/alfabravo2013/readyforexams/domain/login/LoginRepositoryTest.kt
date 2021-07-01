package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class LoginRepositoryTest {
    private val localDataSource = mockk<LocalDataSource>()
    private val loginRepository = LoginRepository(localDataSource)

    private val registeredEmail = "test@test.com"
    private val unregisteredEmail = "unique@test.com"
    private val correctPassword = "123456789"
    private val incorrectPassword = "987654321"

    @Nested
    @DisplayName("login() tests")
    inner class LoginTests {

        @Test
        @DisplayName("Given registered email and correct password when login then Result.Success")
        fun loginWithExistingEmailAndCorrectPassword() {
            every {
                localDataSource.login(registeredEmail, correctPassword)
            } returns Result.Success

            val actual = loginRepository.login(registeredEmail, correctPassword)

            assertTrue(actual is Result.Success)

            verify { localDataSource.login(registeredEmail, correctPassword) }
        }

        @Test
        @DisplayName("Given registered email and incorrect password when login then Result.Failure")
        fun loginWithExistingEmailAndIncorrectPassword() {
            every {
                localDataSource.login(registeredEmail, incorrectPassword)
            } returns Result.Failure()

            val actual = loginRepository.login(registeredEmail, incorrectPassword)

            assertTrue(actual is Result.Failure)

            verify { localDataSource.login(registeredEmail, incorrectPassword) }
        }

        @Test
        @DisplayName("Given unregistered email and correct password when login then Result.Failure")
        fun loginWithNonExistingEmailAndCorrectPassword() {
            every {
                localDataSource.login(unregisteredEmail, correctPassword)
            } returns Result.Failure()

            val actual = loginRepository.login(unregisteredEmail, correctPassword)

            assertTrue(actual is Result.Failure)

            verify { localDataSource.login(unregisteredEmail, correctPassword) }
        }

        @Test
        @DisplayName("Given unregistered email and incorrect password when login then Result.Failure")
        fun loginWithNonExistingEmailAndIncorrectPassword() {
            every {
                localDataSource.login(unregisteredEmail, incorrectPassword)
            } returns Result.Failure()

            val actual = loginRepository.login(unregisteredEmail, incorrectPassword)

            assertTrue(actual is Result.Failure)

            verify { localDataSource.login(unregisteredEmail, incorrectPassword) }
        }
    }

    @Nested
    @DisplayName("signUp() tests")
    inner class SignUpTests {
        @Test
        @DisplayName("Given registered email and any password When signup Then Result.Failure")
        fun signUpWithRegisteredEmail() {
            every {
                localDataSource.signUp(registeredEmail, correctPassword)
            } returns Result.Failure()

            val actual = localDataSource.signUp(registeredEmail, correctPassword)

            Assertions.assertTrue(actual is Result.Failure)

            verify { localDataSource.signUp(registeredEmail, correctPassword) }
        }

        @Test
        @DisplayName("Given unregistered email and any password When signup Then Result.Success")
        fun signUpWithUnregisteredEmail() {
            every {
                localDataSource.signUp(unregisteredEmail, correctPassword)
            } returns Result.Success

            val actual = localDataSource.signUp(unregisteredEmail, correctPassword)

            Assertions.assertTrue(actual is Result.Success)

            verify { localDataSource.signUp(unregisteredEmail, correctPassword) }
        }
    }

    @Nested
    @DisplayName("resetPassword() tests")
    inner class ResetPasswordTest {

        @Test
        @DisplayName("Given registered email When resetPassword Then Result.Success")
        fun resetPasswordWithRegisteredEmail() {
            every {
                localDataSource.resetPassword(registeredEmail)
            } returns Result.Success

            val actual = localDataSource.resetPassword(registeredEmail)

            Assertions.assertTrue(actual is Result.Success)

            verify { localDataSource.resetPassword(registeredEmail) }
        }

        @Test
        @DisplayName("Given unregistered email When resetPassword Then Result.Failure")
        fun resetPasswordWithUnregisteredEmail() {
            every {
                localDataSource.resetPassword(unregisteredEmail)
            } returns Result.Failure()

            val actual = localDataSource.resetPassword(unregisteredEmail)

            Assertions.assertTrue(actual is Result.Failure)

            verify { localDataSource.resetPassword(unregisteredEmail) }
        }
    }
}
