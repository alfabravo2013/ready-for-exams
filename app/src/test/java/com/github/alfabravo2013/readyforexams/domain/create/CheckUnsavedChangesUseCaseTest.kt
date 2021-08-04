package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.domain.models.toTaskRepresentation
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class CheckUnsavedChangesUseCaseTest {
    private val checklistRepository = mockk<ChecklistRepository>()
    private val useCase = CheckUnsavedChangesUseCase(checklistRepository)

    @BeforeEach
    fun setup() {
        every { checklistRepository.storeEditedChecklist() } just Runs
        every { checklistRepository.discardEditedChecklist() } just Runs
    }

    @Test
    @DisplayName("Given blank editedChecklistName and empty createdTasks in ChecklistLocalDataSource, When isSaveChangesRequired, Then return false")
    fun blankChecklistNameAndEmptyTasks() {
        every { checklistRepository.getCreatedTasks() } answers { emptyList() }
        every { checklistRepository.getEditedChecklistName() } returns ""

        val result = useCase.isSaveChangesRequired()

        assertFalse(result)
        verify(exactly = 1) { checklistRepository.getCreatedTasks() }
        verify(exactly = 1) { checklistRepository.getEditedChecklistName() }
        verify(exactly = 1) { checklistRepository.discardEditedChecklist() }
        verify(exactly = 0) { checklistRepository.storeEditedChecklist() }
    }

    @Test
    @DisplayName("Given non-blank editedChecklistName and empty createdTasks in ChecklistLocalDataSource, When isSaveChangesRequired, Then return true")
    fun nonBlankChecklistNameAndEmptyTasks() {
        every { checklistRepository.getCreatedTasks() } answers { emptyList() }
        every { checklistRepository.getEditedChecklistName() } returns "name"

        val result = useCase.isSaveChangesRequired()

        assertTrue(result)
        verify(exactly = 1) { checklistRepository.getCreatedTasks() }
        verify(exactly = 1) { checklistRepository.getEditedChecklistName() }
        verify(exactly = 1) { checklistRepository.storeEditedChecklist() }
        verify(exactly = 0) { checklistRepository.discardEditedChecklist() }
    }

    @Test
    @DisplayName("Given blank editedChecklistName and non-empty createdTasks in ChecklistLocalDataSource, When isSaveChangesRequired, Then return true")
    fun blankChecklistNameAndNonEmptyTasks() {
        val list = listOf(Task("description").toTaskRepresentation())

        every { checklistRepository.getCreatedTasks() } answers { list }
        every { checklistRepository.getEditedChecklistName() } returns ""

        val result = useCase.isSaveChangesRequired()

        assertTrue(result)
        verify(exactly = 1) { checklistRepository.getCreatedTasks() }
        verify(exactly = 1) { checklistRepository.getEditedChecklistName() }
        verify(exactly = 1) { checklistRepository.storeEditedChecklist() }
        verify(exactly = 0) { checklistRepository.discardEditedChecklist() }
    }

    @Test
    @DisplayName("Given non-blank editedChecklistName and non-empty createdTasks in ChecklistLocalDataSource, When isSaveChangesRequired, Then return true")
    fun nonBlankChecklistNameAndNonEmptyTasks() {
        val list = listOf(Task("description").toTaskRepresentation())

        every { checklistRepository.getCreatedTasks() } answers { list }
        every { checklistRepository.getEditedChecklistName() } returns "name"

        val result = useCase.isSaveChangesRequired()

        assertTrue(result)
        verify(exactly = 1) { checklistRepository.getCreatedTasks() }
        verify(exactly = 1) { checklistRepository.getEditedChecklistName() }
        verify(exactly = 1) { checklistRepository.storeEditedChecklist() }
        verify(exactly = 0) { checklistRepository.discardEditedChecklist() }
    }
}
