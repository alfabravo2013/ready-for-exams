package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.domain.models.toTaskRepresentation
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

internal class GetCreatedTasksUseCaseTest {
    private val checklistRepository = mockk<ChecklistRepository>()
    private val useCase = GetCreatedTasksUseCase(checklistRepository)

    @Test
    @DisplayName("Given no tasks were added, When getCreatedTasks, Then return empty list")
    fun getCreatedTasksEmpty() {
        every { checklistRepository.getCreatedTasks() } answers { emptyList() }

        val result = useCase.getCreatedTasks()

        assertTrue(result.isEmpty())
        verify(exactly = 1) { checklistRepository.getCreatedTasks() }
    }

    @Test
    @DisplayName("Given tasks were added, When getCreatedTasks, Then return the list returned by getCreatedTasks of ChecklistRepository")
    fun getCreatedTasksNonEmpty() {
        val taskRepresentation = Task("description").toTaskRepresentation()
        val expected = listOf(taskRepresentation)

        every { checklistRepository.getCreatedTasks() } answers { listOf(taskRepresentation) }

        val actual = useCase.getCreatedTasks()

        assertContentEquals(expected, actual)
        verify(exactly = 1) { checklistRepository.getCreatedTasks() }
    }
}
