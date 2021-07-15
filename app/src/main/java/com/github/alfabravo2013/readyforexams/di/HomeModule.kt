package com.github.alfabravo2013.readyforexams.di

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistLocalDataSource
import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.domain.home.LoadChecklistUseCase
import com.github.alfabravo2013.readyforexams.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { ChecklistLocalDataSource() }
    single { ChecklistRepository(get()) }
    factory { LoadChecklistUseCase(get()) }
    viewModel { HomeViewModel(get()) }
}
