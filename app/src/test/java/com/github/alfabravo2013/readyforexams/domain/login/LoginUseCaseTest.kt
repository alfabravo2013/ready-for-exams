package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

internal class LoginUseCaseTest {
    private val repository = mockk<LoginRepository>()
    private val loginUseCase = LoginUseCase(repository)

    private val registeredEmail = "test@test.com"
    private val unregisteredEmail = "unknown@test.com"
    private val correctPassword = "123456789"

    @Test
    @DisplayName("Given invalid email and any password Then Result.Failure")
    fun invalidEmail() {
        val actual = loginUseCase.login("invalid", correctPassword)

        assertTrue(actual is Result.Failure)
        verify(exactly = 0) { repository.login(any(), any()) }
    }

    @Test
    @DisplayName("Given valid email and invalid password Then Result.Failure")
    fun invalidPassword() {
        val actual = loginUseCase.login(registeredEmail, "password")

        assertTrue(actual is Result.Failure)
        verify(exactly = 0) { repository.login(any(), any()) }
    }

    @Test
    @DisplayName("Given unregistered email and valid password Then Result.Failure")
    fun unregisteredEmail() {
        every { repository.login(not(registeredEmail), any()) } returns Result.Failure()

        val actual = loginUseCase.login(unregisteredEmail, correctPassword)

        assertTrue(actual is Result.Failure)
        verify(exactly = 1) { repository.login(unregisteredEmail, correctPassword) }
    }

    @Test
    @DisplayName("Given registered email and incorrect password Then Result.Failure")
    fun incorrectPassword() {
        val incorrectPassword = correctPassword.reversed()

        every { repository.login(any(), not(correctPassword)) } returns Result.Failure()

        val actual = loginUseCase.login(registeredEmail, incorrectPassword)

        assertTrue(actual is Result.Failure)
        verify(exactly = 1) { repository.login(registeredEmail, incorrectPassword) }
    }

    @Test
    @DisplayName("Given registered email and correct password Then Result.Success")
    fun correctCredentials() {
        every { repository.login(registeredEmail, correctPassword) } returns Result.Success

        val actual = loginUseCase.login(registeredEmail, correctPassword)

        assertTrue(actual is Result.Success)
        verify(exactly = 1) { repository.login(registeredEmail, correctPassword) }
    }
}
