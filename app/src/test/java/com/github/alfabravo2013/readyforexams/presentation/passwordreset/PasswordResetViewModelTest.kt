package com.github.alfabravo2013.readyforexams.presentation.passwordreset

import androidx.lifecycle.Observer
import com.github.alfabravo2013.readyforexams.CoroutinesTestExtension
import com.github.alfabravo2013.readyforexams.InstantExecutorExtension
import com.github.alfabravo2013.readyforexams.domain.passwordreset.PasswordResetUseCase
import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
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
    fun onPasswordResetClickUnregisteredEmail() = runBlocking {
        every {
            passwordResetUseCase.resetPassword(not(registeredEmail))
        } returns Result.Failure("error")

        viewModel.onPasswordResetClicked("email")

        verify(exactly = 1) { passwordResetUseCase.resetPassword("email") }

        verifySequence {
            observer.onChanged(PasswordResetViewModel.OnEvent.ShowProgress)
            observer.onChanged(PasswordResetViewModel.OnEvent.Error("error"))
            observer.onChanged(PasswordResetViewModel.OnEvent.HideProgress)
        }
    }

    @Test
    fun onPasswordResetClickRegisteredEmail() = runBlocking {
        every { passwordResetUseCase.resetPassword(registeredEmail) } returns Result.Success

        viewModel.onPasswordResetClicked(registeredEmail)

        verify(exactly = 1) { passwordResetUseCase.resetPassword(registeredEmail) }

        verifySequence {
            observer.onChanged(PasswordResetViewModel.OnEvent.ShowProgress)
            observer.onChanged(PasswordResetViewModel.OnEvent.ShowDefaultPassword)
            observer.onChanged(PasswordResetViewModel.OnEvent.NavigateToLoginScreen)
            observer.onChanged(PasswordResetViewModel.OnEvent.HideProgress)
        }
    }
}
