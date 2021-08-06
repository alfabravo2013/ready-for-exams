package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class CreateChecklistUseCaseTest {
    private val checklistRepository = mockk<ChecklistRepository>()
    private val useCase = CreateChecklistUseCase(checklistRepository)

    @BeforeEach
    fun setup() {
        every { checklistRepository.discardEditedChecklist() } just Runs
    }

    @Test
    @DisplayName("Given no name was entered, When createChecklist, Then return Result.Failure")
    fun blankName() {
        val name = ""
        every { checklistRepository.getEditedChecklistName() } returns name
        every { checklistRepository.addChecklist(name) } returns Result.Success

        val result = useCase.createChecklist()

        assertTrue(result is Result.Failure)
        verify(exactly = 1) { checklistRepository.getEditedChecklistName() }
        verify(exactly = 0) { checklistRepository.addChecklist(name) }
        verify(exactly = 0) { checklistRepository.discardEditedChecklist() }
    }

    @Test
    @DisplayName("Given non-blank non-unique name, When createChecklist, Then return Result.Failure")
    fun nonUniqueName() {
        val name = "name"
        every { checklistRepository.getEditedChecklistName() } returns name
        every { checklistRepository.addChecklist(name) } returns Result.Failure()

        val result = useCase.createChecklist()

        assertTrue(result is Result.Failure)
        verify(exactly = 1) { checklistRepository.getEditedChecklistName() }
        verify(exactly = 1) { checklistRepository.addChecklist(name) }
        verify(exactly = 0) { checklistRepository.discardEditedChecklist() }
    }

    @Test
    @DisplayName("Given non-blank unique name, When createChecklist, Then return Result.Success")
    fun uniqueName() {
        val name = "name"
        every { checklistRepository.getEditedChecklistName() } returns name
        every { checklistRepository.addChecklist(name) } returns Result.Success

        val result = useCase.createChecklist()

        assertTrue(result is Result.Success)
        verify(exactly = 1) { checklistRepository.getEditedChecklistName() }
        verify(exactly = 1) { checklistRepository.addChecklist(name) }
        verify(exactly = 1) { checklistRepository.discardEditedChecklist() }
    }
}
