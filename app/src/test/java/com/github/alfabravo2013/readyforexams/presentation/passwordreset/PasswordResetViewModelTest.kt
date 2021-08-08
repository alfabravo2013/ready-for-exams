package com.github.alfabravo2013.readyforexams.presentation.passwordreset

import androidx.lifecycle.Observer
import com.github.alfabravo2013.readyforexams.CoroutinesTestExtension
import com.github.alfabravo2013.readyforexams.InstantExecutorExtension
import com.github.alfabravo2013.readyforexams.domain.passwordreset.PasswordResetUseCase
import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
internal class PasswordResetViewModelTest {
    private val passwordResetUseCase = mockk<PasswordResetUseCase>()
    private val viewModel = PasswordResetViewModel(passwordResetUseCase)
    private val observer = mockk<Observer<PasswordResetViewModel.OnEvent>>()

    private val registeredEmail = "test@test.com"

    @BeforeEach
    fun setup() {
        every { observer.onChanged(any()) } just Runs

        viewModel.onEvent.observeForever(observer)
    }

    @Test
    @DisplayName("Given UseCase returns Failure, When onPasswordResetClick Then display error")
    fun onPasswordResetClickUnregisteredEmail() = runBlocking {
        coEvery {
            passwordResetUseCase.resetPassword(not(registeredEmail))
        } returns Result.Failure("error")

        viewModel.onPasswordResetClicked("email")

        coVerify(exactly = 1) { passwordResetUseCase.resetPassword("email") }
        coVerifySequence {
            observer.onChanged(PasswordResetViewModel.OnEvent.ShowProgress)
            observer.onChanged(PasswordResetViewModel.OnEvent.Error("error"))
            observer.onChanged(PasswordResetViewModel.OnEvent.HideProgress)
        }
    }

    @Test
    @DisplayName("Given UseCase returns Success, When onPasswordResetClick Then show password")
    fun onPasswordResetClickRegisteredEmail() = runBlocking {
        coEvery { passwordResetUseCase.resetPassword(registeredEmail) } returns Result.Success

        viewModel.onPasswordResetClicked(registeredEmail)

        coVerify(exactly = 1) { passwordResetUseCase.resetPassword(registeredEmail) }
        coVerifySequence {
            observer.onChanged(PasswordResetViewModel.OnEvent.ShowProgress)
            observer.onChanged(PasswordResetViewModel.OnEvent.ShowDefaultPassword)
            observer.onChanged(PasswordResetViewModel.OnEvent.NavigateToLoginScreen)
            observer.onChanged(PasswordResetViewModel.OnEvent.HideProgress)
        }
    }
}
