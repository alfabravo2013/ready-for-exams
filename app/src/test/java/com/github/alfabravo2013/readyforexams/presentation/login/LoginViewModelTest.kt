package com.github.alfabravo2013.readyforexams.presentation.login

import androidx.lifecycle.Observer
import com.github.alfabravo2013.readyforexams.CoroutinesTestExtension
import com.github.alfabravo2013.readyforexams.InstantExecutorExtension
import com.github.alfabravo2013.readyforexams.domain.login.LoginUseCase
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
    }

    @Test
    @DisplayName("When onSignupLinkClick Then navigate to SignupScreen")
    fun onSignupLinkClick() {
        viewModel.onSignupLinkClick()

        verify { observer.onChanged(LoginViewModel.OnEvent.NavigateToSignupScreen) }
    }

    @Test
    @DisplayName("When onForgotPasswordClick Then navigate to PasswordResetScreen")
    fun onForgotPasswordLinkClick() {
        viewModel.onForgotPasswordLinkClick()

        verify { observer.onChanged(LoginViewModel.OnEvent.NavigateToPasswordResetScreen) }
    }

    @Test
    @DisplayName("Given UseCase returns Success, When onLoginButtonClick Then navigate to HomeScreen")
    fun onLoginButtonClickValidCredentials() = runBlocking {
        coEvery { loginUseCase.login(registeredEmail, correctPassword) } returns Result.Success

        viewModel.onLoginButtonClick(registeredEmail, correctPassword)

        coVerify(exactly = 1) { loginUseCase.login(registeredEmail, correctPassword) }
        coVerifySequence {
            observer.onChanged(LoginViewModel.OnEvent.ShowProgress)
            observer.onChanged(LoginViewModel.OnEvent.NavigateToHomeScreen)
            observer.onChanged(LoginViewModel.OnEvent.HideProgress)
        }
    }

    @Test
    @DisplayName("Given UseCase returns Failure, When onLoginButtonClick Then display error")
    fun onLoginButtonClickInvalidCredentials() = runBlocking {
        coEvery {
            loginUseCase.login(not(registeredEmail), any())
        } returns Result.Failure("error")

        viewModel.onLoginButtonClick("email", correctPassword)

        coVerify(exactly = 1) { loginUseCase.login("email", correctPassword) }
        coVerifySequence {
            observer.onChanged(LoginViewModel.OnEvent.ShowProgress)
            observer.onChanged(LoginViewModel.OnEvent.Error("error"))
            observer.onChanged(LoginViewModel.OnEvent.HideProgress)
        }
    }
}
