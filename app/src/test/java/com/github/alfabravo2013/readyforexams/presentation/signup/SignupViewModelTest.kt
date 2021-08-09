package com.github.alfabravo2013.readyforexams.presentation.signup

import androidx.lifecycle.Observer
import com.github.alfabravo2013.readyforexams.CoroutinesTestExtension
import com.github.alfabravo2013.readyforexams.InstantExecutorExtension
import com.github.alfabravo2013.readyforexams.domain.signup.SignupUseCase
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
internal class SignupViewModelTest {
    private val signupUseCase = mockk<SignupUseCase>()
    private val viewModel = SignupViewModel(signupUseCase)
    private val observer = mockk<Observer<SignupViewModel.OnEvent>>()

    private val email = "test@test.com"
    private val password = "123456789"

    @BeforeEach
    fun setup() {
        viewModel.onEvent.observeForever(observer)

        every { observer.onChanged(any()) } just Runs
    }

    @Test
    @DisplayName("Given UseCase returns Success, when onSignupButtonClick Then onEvent == SignupSuccess")
    fun unregisteredEmailAndValidPassword() = runBlocking {
        coEvery {
            signupUseCase.signup(email, password, password)
        } returns Result.Success

        viewModel.onSignupButtonClick(email, password, password)

        coVerify(exactly = 1) {
            signupUseCase.signup(email, password, password)
        }
        coVerifySequence {
            observer.onChanged(SignupViewModel.OnEvent.ShowProgress)
            observer.onChanged(SignupViewModel.OnEvent.SignupSuccess)
            observer.onChanged(SignupViewModel.OnEvent.HideProgress)
        }
    }

    @Test
    @DisplayName("Given UseCase returns Failure, when onSignupButtonClick Then display error")
    fun registeredEmailAndInvalidPassword() = runBlocking {
        coEvery {
            signupUseCase.signup(email, password, password)
        } returns Result.Failure("error")

        viewModel.onSignupButtonClick(email, password, password)

        coVerify(exactly = 1) {
            signupUseCase.signup(email, password, password)
        }
        coVerifySequence {
            observer.onChanged(SignupViewModel.OnEvent.ShowProgress)
            observer.onChanged(SignupViewModel.OnEvent.Error("error"))
            observer.onChanged(SignupViewModel.OnEvent.HideProgress)
        }
    }

    @Test
    @DisplayName("Given UseCase returns Success, When checkSignupStatus Then onEvent == SignupSuccess")
    fun checkSignupStatus() = runBlocking {
        coEvery {
            signupUseCase.signup(email, password, password)
        } returns Result.Success

        viewModel.onSignupButtonClick(email, password, password)
        viewModel.checkSignupStatus()

        coVerify { observer.onChanged(SignupViewModel.OnEvent.SignupSuccess) }
    }
}
