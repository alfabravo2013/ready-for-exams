package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class LoginRepositoryTest {
    private val localDataSource = spyk<LoginLocalDataSource>()
    private val remoteDataSource = mockk<LoginRemoteDataSource>()
    private val loginRepository = LoginRepository(localDataSource, remoteDataSource)

    private val registeredEmail = "test@test.com"
    private val unregisteredEmail = "unique@test.com"
    private val correctPassword = "123456789"
    private val incorrectPassword = "987654321"

    @Nested
    @DisplayName("When login")
    inner class LoginTests {

        @Test
        @DisplayName("Given registered email and correct password then Result.Success")
        fun loginWithExistingEmailAndCorrectPassword() = runBlocking {
            coEvery {
                remoteDataSource.login(registeredEmail, correctPassword)
            } answers { Result.Success }

            val actual = loginRepository.login(registeredEmail, correctPassword)

            assertTrue(actual is Result.Success)
            coVerify { remoteDataSource.login(registeredEmail, correctPassword) }
        }

        @Test
        @DisplayName("Given registered email and incorrect password Then Result.Failure")
        fun loginWithExistingEmailAndIncorrectPassword() = runBlocking {
            coEvery {
                remoteDataSource.login(registeredEmail, incorrectPassword)
            } answers { Result.Failure("error") }

            val actual = loginRepository.login(registeredEmail, incorrectPassword)

            assertTrue(actual is Result.Failure)
            coVerify { remoteDataSource.login(registeredEmail, incorrectPassword) }
        }

        @Test
        @DisplayName("Given unregistered email and correct password Then Result.Failure")
        fun loginWithNonExistingEmailAndCorrectPassword() = runBlocking {
            coEvery {
                remoteDataSource.login(unregisteredEmail, correctPassword)
            } answers { Result.Failure("error") }

            val actual = loginRepository.login(unregisteredEmail, correctPassword)

            assertTrue(actual is Result.Failure)
            coVerify { remoteDataSource.login(unregisteredEmail, correctPassword) }
        }

        @Test
        @DisplayName("Given unregistered email and incorrect password Then Result.Failure")
        fun loginWithNonExistingEmailAndIncorrectPassword() = runBlocking {
            coEvery {
                remoteDataSource.login(unregisteredEmail, incorrectPassword)
            } answers { Result.Failure("error") }

            val actual = loginRepository.login(unregisteredEmail, incorrectPassword)

            assertTrue(actual is Result.Failure)
            coVerify { remoteDataSource.login(unregisteredEmail, incorrectPassword) }
        }
    }

    @Nested
    @DisplayName("When signup")
    inner class SignUpTests {
        @Test
        @DisplayName("Given registered email and any password Then Result.Failure")
        fun signUpWithRegisteredEmail() = runBlocking {
            coEvery {
                remoteDataSource.signup(registeredEmail, correctPassword)
            } answers { Result.Failure("error") }

            val actual = remoteDataSource.signup(registeredEmail, correctPassword)

            assertTrue(actual is Result.Failure)
            coVerify { remoteDataSource.signup(registeredEmail, correctPassword) }
        }

        @Test
        @DisplayName("Given unregistered email and any password Then Result.Success")
        fun signUpWithUnregisteredEmail() = runBlocking {
            coEvery {
                remoteDataSource.signup(unregisteredEmail, correctPassword)
            } answers { Result.Success }

            val actual = remoteDataSource.signup(unregisteredEmail, correctPassword)

            assertTrue(actual is Result.Success)
            coVerify { remoteDataSource.signup(unregisteredEmail, correctPassword) }
        }
    }

    @Nested
    @DisplayName("When resetPassword")
    inner class ResetPasswordTest {

        @Test
        @DisplayName("Given registered email Then Result.Success")
        fun resetPasswordWithRegisteredEmail() = runBlocking {
            coEvery {
                remoteDataSource.resetPassword(registeredEmail)
            } answers { Result.Success }

            val actual = remoteDataSource.resetPassword(registeredEmail)

            assertTrue(actual is Result.Success)
            coVerify { remoteDataSource.resetPassword(registeredEmail) }
        }

        @Test
        @DisplayName("Given unregistered email Then Result.Failure")
        fun resetPasswordWithUnregisteredEmail() = runBlocking {
            coEvery {
                remoteDataSource.resetPassword(unregisteredEmail)
            } answers { Result.Failure("error") }

            val actual = remoteDataSource.resetPassword(unregisteredEmail)

            assertTrue(actual is Result.Failure)
            coVerify { remoteDataSource.resetPassword(unregisteredEmail) }
        }
    }
}
