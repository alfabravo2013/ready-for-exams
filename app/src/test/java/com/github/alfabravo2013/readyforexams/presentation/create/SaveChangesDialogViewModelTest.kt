package com.github.alfabravo2013.readyforexams.presentation.create

import androidx.lifecycle.Observer
import com.github.alfabravo2013.readyforexams.CoroutinesTestExtension
import com.github.alfabravo2013.readyforexams.InstantExecutorExtension
import com.github.alfabravo2013.readyforexams.domain.create.SaveChangesUseCase
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
internal class SaveChangesDialogViewModelTest {
    private val saveChangesUseCase = mockk<SaveChangesUseCase>()
    private val viewModel = SaveChangesDialogViewModel(saveChangesUseCase)
    private val observer = mockk<Observer<SaveChangesDialogViewModel.OnEvent>>()

    @BeforeEach
    fun setup() {
        viewModel.onEvent.observeForever(observer)

        every { observer.onChanged(any()) } just Runs
    }

    @Test
    @DisplayName("When onPositiveButtonClick, Then call saveChanges and set event to NavigateToHomeScreen")
    fun onDialogPositiveButtonCLick() {
        every { saveChangesUseCase.saveChanges() } just Runs

        viewModel.onPositiveButtonClick()

        verifySequence {
            saveChangesUseCase.saveChanges()
            observer.onChanged(SaveChangesDialogViewModel.OnEvent.NavigateToHomeScreen)
        }
    }

    @Test
    @DisplayName("When onNegativeButtonClick, Then call discardChanges and set event to NavigateToHomeScreen")
    fun onDialogNegativeButtonCLick() {
        every { saveChangesUseCase.discardChanges() } just Runs

        viewModel.onNegativeButtonClick()

        verifySequence {
            saveChangesUseCase.discardChanges()
            observer.onChanged(SaveChangesDialogViewModel.OnEvent.NavigateToHomeScreen)
        }
    }
}
