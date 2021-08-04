package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class AddTaskUseCaseTest {
    private val checklistRepository = mockk<ChecklistRepository>()
    private val useCase = AddTaskUseCase(checklistRepository)

    @Test
    @DisplayName("Given LocalDataSource has empty or blank editedDescription, When addTask, Then return Result.Failure")
    fun saveTaskWithBlankDescription() {
        val description = ""

        every { checklistRepository.getEditedTaskDescription() } returns description
        every { checklistRepository.addTask(description) } just Runs

        val result = useCase.addTask()

        assertTrue(result is Result.Failure)
        verify(exactly = 1) { checklistRepository.getEditedTaskDescription() }
        verify(exactly = 0) { checklistRepository.addTask(description) }
    }

    @Test
    @DisplayName("Given LocalDataSource has non-blank editedDescription, When addTask, Then return Result.Success")
    fun saveTaskWithNonBlankDescription() {
        val description = "description"

        every { checklistRepository.getEditedTaskDescription() } returns description
        every { checklistRepository.addTask(description) } just Runs

        val result = useCase.addTask()

        assertTrue(result is Result.Success)
        verify(exactly = 1) { checklistRepository.getEditedTaskDescription() }
        verify(exactly = 1) { checklistRepository.addTask(description) }
    }
}
