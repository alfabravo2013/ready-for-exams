package com.github.alfabravo2013.readyforexams.di

import com.github.alfabravo2013.readyforexams.presentation.home.HomeViewModel
import com.github.alfabravo2013.readyforexams.presentation.landing.LandingViewModel
import com.github.alfabravo2013.readyforexams.presentation.login.LoginViewModel
import com.github.alfabravo2013.readyforexams.presentation.passwordreset.PasswordResetViewModel
import com.github.alfabravo2013.readyforexams.presentation.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LandingViewModel() }
    viewModel { SignupViewModel() }
    viewModel { PasswordResetViewModel() }
    viewModel { HomeViewModel() }
}
