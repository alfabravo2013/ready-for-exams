package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class UpdateEditedDataUseCaseTest {
    private val checklistRepository = mockk<ChecklistRepository>()
    private val useCase = UpdateEditedDataUseCase(checklistRepository)

    @BeforeEach
    fun setup() {
        every { checklistRepository.setEditedChecklistName(any()) } just Runs
        every { checklistRepository.setEditedTaskDescription(any()) } just Runs
    }

    @Test
    @DisplayName("Given any blank string, When setEditedChecklistName, Then do nothing")
    fun setBlankName() {
        useCase.setEditedChecklistName("")

        verify(exactly = 0) { checklistRepository.setEditedChecklistName("") }
    }

    @Test
    @DisplayName("Given non-blank string, When setEditedChecklistName, Then call repo's setEditedChecklistName with the same string")
    fun setNonBlankName() {
        useCase.setEditedChecklistName("name")

        verify(exactly = 1) { checklistRepository.setEditedChecklistName("name") }
    }

    @Test
    @DisplayName("Given any blank string, When setEditedTaskDescription, Then do nothing")
    fun setBlankDescription() {
        useCase.setEditedTaskDescription("")

        verify(exactly = 0) { checklistRepository.setEditedTaskDescription("") }
    }

    @Test
    @DisplayName("Given non-blank string, When setEditedTaskDescription, Then call repo's setEditedTaskDescription with the same string")
    fun setNonBlankDescription() {
        useCase.setEditedTaskDescription("description")

        verify(exactly = 1) { checklistRepository.setEditedTaskDescription("description") }
    }
}
