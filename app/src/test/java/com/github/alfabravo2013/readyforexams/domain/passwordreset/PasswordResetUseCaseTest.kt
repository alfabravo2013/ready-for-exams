package com.github.alfabravo2013.readyforexams.domain.passwordreset

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

internal class PasswordResetUseCaseTest {
    private val loginRepository = mockk<LoginRepository>()
    private val passwordResetUseCase = PasswordResetUseCase(loginRepository)

    private val registeredEmail = "test@test.com"

    @Test
    @DisplayName("Given invalid email Then Result.Failure")
    fun invalidEmail() = runBlocking {
        val actual = passwordResetUseCase.resetPassword("")

        assertTrue(actual is Result.Failure)
        coVerify(exactly = 0) { loginRepository.resetPassword(any()) }
    }

    @Test
    @DisplayName("Given unregistered email Then Result.Failure")
    fun unregisteredEmail() = runBlocking {
        val unregisteredEmail = "unknown@test.com"

        coEvery { loginRepository.resetPassword(not(registeredEmail)) } returns Result.Failure()

        val actual = passwordResetUseCase.resetPassword(unregisteredEmail)

        assertTrue(actual is Result.Failure)
        coVerify(exactly = 1) { loginRepository.resetPassword(unregisteredEmail) }
    }

    @Test
    @DisplayName("Given registered email Then Result.Success")
    fun registeredEmail() = runBlocking {
        coEvery { loginRepository.resetPassword(registeredEmail) } returns Result.Success

        val actual = passwordResetUseCase.resetPassword(registeredEmail)

        assertTrue(actual is Result.Success)
        coVerify(exactly = 1) { loginRepository.resetPassword(registeredEmail) }
    }
}
