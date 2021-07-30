package com.github.alfabravo2013.readyforexams.di

import com.github.alfabravo2013.readyforexams.MainViewModel
import com.github.alfabravo2013.readyforexams.presentation.landing.LandingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LandingViewModel() }
    viewModel { MainViewModel() }
}
