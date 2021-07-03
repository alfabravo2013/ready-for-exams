package com.github.alfabravo2013.readyforexams.domain.signup

import com.github.alfabravo2013.readyforexams.domain.login.LoginRepository
import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class SignupUseCaseTest {

    @Test
    @DisplayName("Given invalid email and any password and confirmed password Then Result.Failure")
    fun invalidEmail() {
        val repository = mockk<LoginRepository>()
        every { repository.signUp(any(), any()) } returns Result.Failure()
        val signupUseCase = SignupUseCase(repository)

        val actual = signupUseCase.signup("email", "123456789", "123456789")

        assertTrue(actual is Result.Failure)

        verify(exactly = 0) { repository.signUp(any(), any()) }
    }

    @Test
    @DisplayName("Given valid email and invalid password and any confirmed password Then Result.Failure")
    fun invalidPassword() {
        val repository = mockk<LoginRepository>()
        every { repository.signUp(any(), any()) } returns Result.Failure()
        val signupUseCase = SignupUseCase(repository)

        val actual = signupUseCase.signup("test@test.com", "12345678", "123456789")

        assertTrue(actual is Result.Failure)

        verify(exactly = 0) { repository.signUp(any(), any()) }
    }

    @Test
    @DisplayName("Given valid email and valid password and password != confirmed password Then Result.Failure")
    fun nonMatchingPasswords() {
        val repository = mockk<LoginRepository>()
        every { repository.signUp(any(), any()) } returns Result.Failure()
        val signupUseCase = SignupUseCase(repository)

        val actual = signupUseCase.signup("test@test.com", "123456789", "123456780")

        assertTrue(actual is Result.Failure)

        verify(exactly = 0) { repository.signUp(any(), any()) }
    }

    @Test
    @DisplayName("Given unregistered email and valid password and confirmed password Then Result.Failure")
    fun unregisteredEmail() {
        val unregisteredEmail = "email@email.com"
        val password = "123456789"

        val repository = mockk<LoginRepository>()
        every { repository.signUp(unregisteredEmail, any()) } returns Result.Failure()
        val signupUseCase = SignupUseCase(repository)

        val actual = signupUseCase.signup(unregisteredEmail, password, password)

        assertTrue(actual is Result.Failure)

        verify(exactly = 1) { repository.signUp(unregisteredEmail, password) }
    }

    @Test
    @DisplayName("Given registered email and incorrect password Then Result.Failure")
    fun incorrectPassword() {
        val registeredEmail = "test@test.com"
        val correctPassword = "123456789"
        val incorrectPassword = correctPassword.reversed()

        val repository = mockk<LoginRepository>()
        every { repository.signUp(registeredEmail, not(correctPassword)) } returns Result.Failure()
        val signupUseCase = SignupUseCase(repository)

        val actual = signupUseCase.signup(registeredEmail, incorrectPassword, incorrectPassword)

        assertTrue(actual is Result.Failure)

        verify(exactly = 1) { repository.signUp(registeredEmail, incorrectPassword) }
    }

    @Test
    @DisplayName("Given registered email and correct password and confirmed password Then Result.Success")
    fun correctCredentials() {
        val registeredEmail = "test@test.com"
        val correctPassword = "123456789"

        val repository = mockk<LoginRepository>()
        every { repository.signUp(registeredEmail, correctPassword) } returns Result.Success
        val signupUseCase = SignupUseCase(repository)

        val actual = signupUseCase.signup(registeredEmail, correctPassword, correctPassword)

        assertTrue(actual is Result.Success)

        verify(exactly = 1) { repository.signUp(registeredEmail, correctPassword) }
    }
}