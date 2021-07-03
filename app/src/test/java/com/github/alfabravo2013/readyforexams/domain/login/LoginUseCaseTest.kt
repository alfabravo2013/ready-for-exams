package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

internal class LoginUseCaseTest {

    @Test
    @DisplayName("Given invalid email and any password Then Result.Failure")
    fun invalidEmail() {
        val repository = mockk<LoginRepository>()
        every { repository.login(any(), any()) } returns Result.Failure()
        val loginUseCase = LoginUseCase(repository)

        val actual = loginUseCase.login("invalid", "123456789")

        assertTrue(actual is Result.Failure)

        verify(exactly = 0) { repository.login(any(), any()) }
    }

    @Test
    @DisplayName("Given valid email and invalid password Then Result.Failure")
    fun invalidPassword() {
        val repository = mockk<LoginRepository>()
        every { repository.login(any(), any()) } returns Result.Failure()
        val loginUseCase = LoginUseCase(repository)

        val actual = loginUseCase.login("email@email.com", "password")

        assertTrue(actual is Result.Failure)

        verify(exactly = 0) { repository.login(any(), any()) }
    }

    @Test
    @DisplayName("Given unregistered email and valid password Then Result.Failure")
    fun unregisteredEmail() {
        val unregisteredEmail = "email@email.com"

        val repository = mockk<LoginRepository>()
        every { repository.login(unregisteredEmail, any()) } returns Result.Failure()
        val loginUseCase = LoginUseCase(repository)

        val actual = loginUseCase.login(unregisteredEmail, "123456789")

        assertTrue(actual is Result.Failure)

        verify(exactly = 1) { repository.login(unregisteredEmail, any()) }
    }

    @Test
    @DisplayName("Given registered email and incorrect password Then Result.Failure")
    fun incorrectPassword() {
        val registeredEmail = "test@test.com"
        val correctPassword = "123456789"
        val incorrectPassword = correctPassword.reversed()

        val repository = mockk<LoginRepository>()
        every {
            repository.login(registeredEmail, not(correctPassword))
        } returns Result.Failure()
        val loginUseCase = LoginUseCase(repository)

        val actual = loginUseCase.login(registeredEmail, incorrectPassword)

        assertTrue(actual is Result.Failure)

        verify(exactly = 1) { repository.login(registeredEmail, incorrectPassword) }
    }

    @Test
    @DisplayName("Given registered email and correct password Then Result.Success")
    fun correctCredentials() {
        val registeredEmail = "test@test.com"
        val correctPassword = "123456789"

        val repository = mockk<LoginRepository>()
        every { repository.login(registeredEmail, correctPassword) } returns Result.Success
        val loginUseCase = LoginUseCase(repository)

        val actual = loginUseCase.login(registeredEmail, correctPassword)

        assertTrue(actual is Result.Success)

        verify(exactly = 1) { repository.login(registeredEmail, correctPassword) }
    }
}