package com.github.alfabravo2013.readyforexams.di

import com.github.alfabravo2013.readyforexams.domain.login.LocalDataSource
import com.github.alfabravo2013.readyforexams.domain.login.LoginRepository
import com.github.alfabravo2013.readyforexams.domain.login.LoginUseCase
import com.github.alfabravo2013.readyforexams.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    single { LocalDataSource() }
    single { LoginRepository(get()) }
    factory { LoginUseCase(get()) }
    viewModel { LoginViewModel(get()) }
}