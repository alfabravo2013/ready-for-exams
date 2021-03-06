package com.github.alfabravo2013.readyforexams.domain.signup

import com.github.alfabravo2013.readyforexams.domain.login.LoginRepository
import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class SignupUseCaseTest {
    private val repository = mockk<LoginRepository>()
    private val signupUseCase = SignupUseCase(repository)

    private val registeredEmail = "test@test.com"
    private val unregisteredEmail = "email@email.com"
    private val validPassword = "123456789"
    private val invalidPassword = "12345678"

    @Test
    @DisplayName("Given invalid email and any password and confirmed password Then Result.Failure")
    fun invalidEmail() = runBlocking {
        val actual = signupUseCase.signup("email", validPassword, validPassword)

        assertTrue(actual is Result.Failure)
        coVerify(exactly = 0) { repository.signup(any(), any()) }
    }

    @Test
    @DisplayName("Given valid email and invalid password and any confirmed password Then Result.Failure")
    fun invalidPassword() = runBlocking {
        val actual = signupUseCase.signup(unregisteredEmail, invalidPassword, invalidPassword)

        assertTrue(actual is Result.Failure)
        coVerify(exactly = 0) { repository.signup(any(), any()) }
    }

    @Test
    @DisplayName("Given valid email and valid password and password != confirmed password Then Result.Failure")
    fun nonMatchingPasswords() = runBlocking {
        val confirmedPassword = validPassword + "0"

        val actual = signupUseCase.signup(unregisteredEmail, validPassword, confirmedPassword)

        assertTrue(actual is Result.Failure)
        coVerify(exactly = 0) { repository.signup(any(), any()) }
    }

    @Test
    @DisplayName("Given unregistered email and valid password and confirmed password Then Result.Success")
    fun unregisteredEmail() = runBlocking {
        coEvery { repository.signup(unregisteredEmail, validPassword) } returns Result.Success

        val actual = signupUseCase.signup(unregisteredEmail, validPassword, validPassword)

        assertTrue(actual is Result.Success)
        coVerify(exactly = 1) { repository.signup(unregisteredEmail, validPassword) }
    }

    @Test
    @DisplayName("Given registered email and and valid password and confirmed password Then Result.Failure")
    fun registeredEmail() = runBlocking {
        coEvery { repository.signup(not(unregisteredEmail), any()) } returns Result.Failure()

        val actual = signupUseCase.signup(registeredEmail, validPassword, validPassword)

        assertTrue(actual is Result.Failure)
        coVerify(exactly = 1) { repository.signup(registeredEmail, validPassword) }
    }
}
