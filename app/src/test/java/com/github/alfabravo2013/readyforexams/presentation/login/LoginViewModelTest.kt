package com.github.alfabravo2013.readyforexams.presentation.login

import com.github.alfabravo2013.readyforexams.InstantExecutorExtension
import com.github.alfabravo2013.readyforexams.domain.login.LoginUseCase
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class LoginViewModelTest {
    private val loginUseCase = mockk<LoginUseCase>()
    private val viewModel = LoginViewModel(loginUseCase)

    @Test
    fun navigateToSignupScreen() = runBlocking {
        viewModel.onSignupLinkClick()

        val actual = viewModel.onEvent.value

        assertNotNull(actual)
        assertTrue(actual is LoginViewModel.OnEvent.NavigateToSignupScreen)
    }

    @Test
    fun navigateToResetPasswordScreen() = runBlocking {
        viewModel.onForgotPasswordLinkClick()

        val actual = viewModel.onEvent.value

        assertNotNull(actual)
        assertTrue(actual is LoginViewModel.OnEvent.NavigateToPasswordResetScreen)
    }


}
