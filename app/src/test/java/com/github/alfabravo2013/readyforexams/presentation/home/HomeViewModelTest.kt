package com.github.alfabravo2013.readyforexams.presentation.home

import androidx.lifecycle.Observer
import com.github.alfabravo2013.readyforexams.CoroutinesTestExtension
import com.github.alfabravo2013.readyforexams.InstantExecutorExtension
import com.github.alfabravo2013.readyforexams.domain.home.LoadChecklistUseCase
import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.toChecklistRepresentation
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
internal class HomeViewModelTest {
    private val loadChecklistUseCase = mockk<LoadChecklistUseCase>()
    private val viewModel = HomeViewModel(loadChecklistUseCase)
    private val observer = mockk<Observer<HomeViewModel.OnEvent>>()

    @BeforeEach
    fun setup() {
        viewModel.onEvent.observeForever(observer)

        every { observer.onChanged(any()) } just Runs
    }

    @Test
    @DisplayName("When onAddChecklistButtonClick Then navigate to Create screen")
    fun onAddChecklistButtonClickTest() {
        viewModel.onAddChecklistButtonClick()

        verify { observer.onChanged(HomeViewModel.OnEvent.NavigateToCreateScreen) }
    }

    @Test
    @DisplayName("Given no checklists have been added, When fetchChecklists(), Then show empty list")
    fun fetchChecklistsEmptyList() {
        every { loadChecklistUseCase.getChecklists() } answers { emptyList() }

        viewModel.fetchChecklists()

        verifySequence {
            observer.onChanged(HomeViewModel.OnEvent.ShowProgress)
            observer.onChanged(HomeViewModel.OnEvent.EmptyList)
            observer.onChanged(HomeViewModel.OnEvent.HideProgress)
        }
    }

    @Test
    @DisplayName("Given some checklists have been added, When fetchChecklists(), Then show a list of these checklists")
    fun fetchChecklistsNonEmptyList() {
        val checklist = Checklist("name", listOf())
        val checklistRepresentation = checklist.toChecklistRepresentation()

        every { loadChecklistUseCase.getChecklists() } answers { listOf(checklistRepresentation) }

        viewModel.fetchChecklists()

        verifySequence {
            observer.onChanged(HomeViewModel.OnEvent.ShowProgress)
            observer.onChanged(HomeViewModel.OnEvent.LoadChecklists(listOf(checklistRepresentation)))
            observer.onChanged(HomeViewModel.OnEvent.HideProgress)
        }
    }
}
