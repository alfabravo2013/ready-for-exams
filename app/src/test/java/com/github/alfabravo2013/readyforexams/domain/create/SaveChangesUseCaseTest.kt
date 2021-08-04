package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class SaveChangesUseCaseTest {
    private val checklistRepository = mockk<ChecklistRepository>()
    private val useCase = SaveChangesUseCase(checklistRepository)

    @Test
    @DisplayName("Given any case, When saveChanges, Then call saveEditedChecklist of ChecklistRepository")
    fun saveChanges() {
        every { checklistRepository.saveEditedChecklist() } just Runs
        every { checklistRepository.discardEditedChecklist() } just Runs

        useCase.saveChanges()

        verify(exactly = 1) { checklistRepository.saveEditedChecklist() }
        verify(exactly = 0) { checklistRepository.discardEditedChecklist() }
    }

    @Test
    @DisplayName("Given any case, When discardChanges, Then call discardEditedChecklist of ChecklistRepository")
    fun discardChanges() {
        every { checklistRepository.discardEditedChecklist() } just Runs
        every { checklistRepository.saveEditedChecklist() } just Runs

        useCase.discardChanges()

        verify(exactly = 1) { checklistRepository.discardEditedChecklist() }
        verify(exactly = 0) { checklistRepository.saveEditedChecklist() }
    }
}
