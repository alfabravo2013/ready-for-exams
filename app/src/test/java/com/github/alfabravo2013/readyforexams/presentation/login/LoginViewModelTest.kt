package com.github.alfabravo2013.readyforexams.presentation.login

import androidx.lifecycle.Observer
import com.github.alfabravo2013.readyforexams.CoroutinesTestExtension
import com.github.alfabravo2013.readyforexams.InstantExecutorExtension
import com.github.alfabravo2013.readyforexams.domain.login.LoginUseCase
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
internal class LoginViewModelTest {
    private val loginUseCase = mockk<LoginUseCase>()
    private val viewModel = LoginViewModel(loginUseCase)
    private val observer = mockk<Observer<LoginViewModel.OnEvent>>()

    private val registeredEmail = "test@test.com"
    private val correctPassword = "123456789"

    @BeforeEach
    fun setup() {
        viewModel.onEvent.observeForever(observer)

        every { observer.onChanged(any()) } just Runs
        every { loginUseCase.login(registeredEmail, correctPassword) } returns Result.Success
        every {
            loginUseCase.login(not(registeredEmail), any())
        } returns Result.Failure("error")
    }

    @Test
    fun onSignupLinkClick() = runBlocking {
        viewModel.onSignupLinkClick()

        verify { observer.onChanged(LoginViewModel.OnEvent.NavigateToSignupScreen) }
    }

    @Test
    fun onForgotPasswordLinkClick() = runBlocking {
        viewModel.onForgotPasswordLinkClick()

        verify { observer.onChanged(LoginViewModel.OnEvent.NavigateToPasswordResetScreen) }
    }

    @Test
    fun onLoginButtonClickValidCredentials() = runBlocking {
        viewModel.onLoginButtonClick(registeredEmail, correctPassword)

        verify(exactly = 1) { loginUseCase.login(registeredEmail, correctPassword) }

        verifySequence {
            observer.onChanged(LoginViewModel.OnEvent.ShowProgress)
            observer.onChanged(LoginViewModel.OnEvent.NavigateToHomeScreen)
            observer.onChanged(LoginViewModel.OnEvent.HideProgress)
        }
    }

    @Test
    fun onLoginButtonClickInvalidCredentials() = runBlocking {
        viewModel.onLoginButtonClick("email", correctPassword)

        verify(exactly = 1) { loginUseCase.login("email", correctPassword) }

        verifySequence {
            observer.onChanged(LoginViewModel.OnEvent.ShowProgress)
            observer.onChanged(LoginViewModel.OnEvent.Error("error"))
            observer.onChanged(LoginViewModel.OnEvent.HideProgress)
        }
    }
}
