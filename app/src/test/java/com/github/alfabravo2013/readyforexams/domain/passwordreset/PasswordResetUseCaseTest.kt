package com.github.alfabravo2013.readyforexams.domain.passwordreset

import com.github.alfabravo2013.readyforexams.domain.login.LoginRepository
import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

internal class PasswordResetUseCaseTest {
    private val loginRepository = mockk<LoginRepository>()
    private val passwordResetUseCase = PasswordResetUseCase(loginRepository)

    @Test
    @DisplayName("Given invalid email Then Result.Failure")
    fun invalidEmail() {
        every { loginRepository.resetPassword(any()) } returns Result.Failure()

        val actual = passwordResetUseCase.resetPassword("")

        assertTrue(actual is Result.Failure)

        verify(exactly = 0) { loginRepository.resetPassword(any()) }
    }

    @Test
    @DisplayName("Given unregistered email Then Result.Failure")
    fun unregisteredEmail() {
        val unregisteredEmail = "unknown@test.com"

        every { loginRepository.resetPassword(unregisteredEmail) } returns Result.Failure()

        val actual = passwordResetUseCase.resetPassword(unregisteredEmail)

        assertTrue(actual is Result.Failure)

        verify(exactly = 1) { loginRepository.resetPassword(unregisteredEmail) }
    }

    @Test
    @DisplayName("Given registered email Then Result.Success")
    fun registeredEmail() {
        val registeredEmail = "test@test.com"

        every { loginRepository.resetPassword(registeredEmail) } returns Result.Success

        val actual = passwordResetUseCase.resetPassword(registeredEmail)

        assertTrue(actual is Result.Success)

        verify(exactly = 1) { loginRepository.resetPassword(registeredEmail) }
    }
}
