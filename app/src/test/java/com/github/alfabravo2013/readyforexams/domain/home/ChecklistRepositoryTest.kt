package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.domain.models.toChecklistRepresentation
import com.github.alfabravo2013.readyforexams.domain.models.toTaskRepresentation
import com.github.alfabravo2013.readyforexams.presentation.models.TaskRepresentation
import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ChecklistRepositoryTest {
    private val checklistLocalDataSource = mockk<ChecklistLocalDataSource>()
    private val checklistRepository = ChecklistRepository(checklistLocalDataSource)

    private val name = "name"
    private val checklist = Checklist(name, listOf())
    private val checklistRepresentation = checklist.toChecklistRepresentation()

    @Nested
    @DisplayName("When getChecklists")
    inner class GetChecklistsTest {

        @Test
        @DisplayName("When no checklists have been added yet, Then return empty list")
        fun getChecklistsEmptyList() {
            every { checklistLocalDataSource.getChecklists() } answers { emptyList() }

            val checklists = checklistRepository.getChecklists()

            assertEquals(0, checklists.size)
            verify(exactly = 1) { checklistLocalDataSource.getChecklists() }
        }

        @Test
        @DisplayName("When 1 checklist has been added, Then return a list size == 1")
        fun getChecklistsNonEmptyList() {
            every { checklistLocalDataSource.getChecklists() } answers { listOf(checklist) }

            val checklists = checklistRepository.getChecklists()

            assertEquals(1, checklists.size)
            verify(exactly = 1) { checklistLocalDataSource.getChecklists() }
        }

        @Test
        @DisplayName("When a checklist have been added, Then return a list comprising the corresponding ChecklistRepresentation")
        fun getChecklistsContainsAddedChecklists() {
            every { checklistLocalDataSource.getChecklists() } answers { listOf(checklist) }

            val retrievedChecklists = checklistRepository.getChecklists()

            assertEquals(listOf(checklistRepresentation), retrievedChecklists)
            verify(exactly = 1) { checklistLocalDataSource.getChecklists() }
        }
    }

    @Nested
    @DisplayName("When addChecklist")
    inner class AddChecklistTest {

        @Test
        @DisplayName("Given a unique checklist name Then return Result.Success")
        fun addChecklistWithUniqueName() {
            every { checklistLocalDataSource.addChecklist(checklist) } returns Result.Success
            every { checklistLocalDataSource.getCreatedTasks() } answers { checklist.tasks }
            every { checklistLocalDataSource.clearCreatedTasks() } just Runs

            val result = checklistRepository.addChecklist(name)

            assertTrue(result is Result.Success)
            verify(exactly = 1) { checklistLocalDataSource.addChecklist(checklist) }
        }

        @Test
        @DisplayName("Given a non-unique checklist name Then return Result.Failure")
        fun addChecklistWithNonUniqueName() {
            every { checklistLocalDataSource.addChecklist(checklist) } returns Result.Failure()
            every { checklistLocalDataSource.getCreatedTasks() } answers { checklist.tasks }

            val result = checklistRepository.addChecklist(name)

            assertTrue(result is Result.Failure)
            verify(exactly = 1) { checklistLocalDataSource.addChecklist(checklist) }
        }
    }

    @Nested
    @DisplayName("When updateChecklist")
    inner class UpdateChecklistTest {

        @Test
        @DisplayName("Given an existing checklist name, Then return Result.Success")
        fun updateExistingChecklist() {
            every { checklistLocalDataSource.updateChecklist(checklist) } returns Result.Success

            val result = checklistRepository.updateChecklist(checklist)

            assertTrue(result is Result.Success)
            verify(exactly = 1) { checklistLocalDataSource.updateChecklist(checklist) }
        }

        @Test
        @DisplayName("Given a non-existing checklist name, Then return Result.Failure")
        fun updateNonExistingChecklist() {
            every { checklistLocalDataSource.updateChecklist(checklist) } returns Result.Failure()

            val result = checklistRepository.updateChecklist(checklist)

            assertTrue(result is Result.Failure)
            verify(exactly = 1) { checklistLocalDataSource.updateChecklist(checklist) }
        }
    }

    @Nested
    @DisplayName("When deleteChecklistByName")
    inner class DeleteChecklistByNameTest {

        @Test
        @DisplayName("Given any checklist name, Then call ChecklistLocalDataSource.deleteChecklistByName with this name")
        fun deleteChecklistByName() {
            every { checklistLocalDataSource.deleteChecklistByName(any()) } just Runs

            checklistRepository.deleteChecklistByName("name")

            verify(exactly = 1) { checklistLocalDataSource.deleteChecklistByName("name") }
        }
    }

    @Nested
    @DisplayName("created Tasks")
    inner class CreatedTasksTest {
        private val description = "description"
        private val task = Task(description)

        @Test
        @DisplayName("Given no tasks added, When getCreatedTasks, Then returns empty list")
        fun getCreatedTasksEmpty() {
            every { checklistLocalDataSource.getCreatedTasks() } answers { emptyList() }

            val tasks: List<TaskRepresentation> = checklistRepository.getCreatedTasks()

            assertTrue(tasks.isEmpty())
            verify(exactly = 1) { checklistLocalDataSource.getCreatedTasks() }
        }

        @Test
        @DisplayName("Given a single task was added, When getCreatedTasks, Then return the list size == 1")
        fun getCreatedTasksNonEmpty() {
            every { checklistLocalDataSource.getCreatedTasks() } answers { listOf(task) }

            val tasks: List<TaskRepresentation> = checklistRepository.getCreatedTasks()

            assertEquals(1, tasks.size)
            verify(exactly = 1) { checklistLocalDataSource.getCreatedTasks() }
        }

        @Test
        @DisplayName("Given a single task was added, When getCreatedTasks, Then return TaskRepresentation of the same task")
        fun getCreatedTasksSame() {
            every { checklistLocalDataSource.getCreatedTasks() } answers { listOf(task) }

            val tasks = checklistRepository.getCreatedTasks()

            assertEquals(task.toTaskRepresentation(), tasks.first())
            verify(exactly = 1) { checklistLocalDataSource.getCreatedTasks() }
        }

        @Test
        @DisplayName("Given any string, When addTask, Then call addCreatedTask of ChecklistLocalDataSource")
        fun addTask() {
            every { checklistLocalDataSource.addCreatedTask(task) } returns true

            checklistRepository.addTask(description)

            verify { checklistLocalDataSource.addCreatedTask(task) }
        }
    }

    @Nested
    @DisplayName("EditedChecklist")
    inner class EditedChecklistTest {

        @Test
        @DisplayName("Given any case, When storeEditedChecklist, Then call storeEditedChecklist of ChecklistLocalDataSource")
        fun storeEditedChecklist() {
            every { checklistLocalDataSource.storeEditedChecklist() } just Runs

            checklistRepository.storeEditedChecklist()

            verify(exactly = 1) { checklistLocalDataSource.storeEditedChecklist() }
        }

        @Test
        @DisplayName("Given any case, When saveEditedChecklist, Then call saveEditedChecklist of ChecklistLocalDataSource")
        fun saveEditedChecklist() {
            every { checklistLocalDataSource.saveEditedChecklist() } just Runs

            checklistRepository.saveEditedChecklist()

            verify(exactly = 1) { checklistLocalDataSource.saveEditedChecklist() }
        }

        @Test
        @DisplayName("Given any case, When discardEditedChecklist, Then call discardEditedChecklist of ChecklistLocalDataSource")
        fun discardEditedChecklist() {
            every { checklistLocalDataSource.discardEditedChecklist() } just Runs

            checklistRepository.discardEditedChecklist()

            verify(exactly = 1) { checklistLocalDataSource.discardEditedChecklist() }
        }
    }

    @Nested
    @DisplayName("EditedChecklistName")
    inner class EditedChecklistName {

        @Test
        @DisplayName("Given any string, When setEditedChecklistName, Then call setEditedChecklistName of ChecklistLocalDataSource with the same string")
        fun setEditedChecklistName() {
            every { checklistLocalDataSource.setEditedChecklistName(name) } just Runs

            checklistRepository.setEditedChecklistName(name)

            verify(exactly = 1) { checklistLocalDataSource.setEditedChecklistName(name) }
        }

        @Test
        @DisplayName("Given any case, When getEditedChecklistName, Then return the name returned by getEditedChecklistName of ChecklistLocalDataSource")
        fun getEditedChecklistName() {
            every { checklistRepository.getEditedChecklistName() } returns name

            val result = checklistRepository.getEditedChecklistName()

            assertEquals(name, result)
            verify(exactly = 1) { checklistLocalDataSource.getEditedChecklistName() }
        }
    }

    @Nested
    @DisplayName("EditedTaskDescription")
    inner class EditedTaskDescription {
        private val description = "description"

        @Test
        @DisplayName("Given any string, When setEditedTaskDescription, Then call setEditedTaskDescription of ChecklistLocalDataSource with the same string")
        fun setEditedChecklistName() {
            every { checklistLocalDataSource.setEditedTaskDescription(description) } just Runs

            checklistRepository.setEditedTaskDescription(description)

            verify(exactly = 1) { checklistLocalDataSource.setEditedTaskDescription(description) }
        }

        @Test
        @DisplayName("Given any case, When getEditedTaskDescription, Then return the description returned by getEditedTaskDescription of ChecklistLocalDataSource")
        fun getEditedChecklistName() {
            every { checklistRepository.getEditedTaskDescription() } returns description

            val result = checklistRepository.getEditedTaskDescription()

            assertEquals(description, result)
            verify(exactly = 1) { checklistLocalDataSource.getEditedTaskDescription() }
        }
    }
}
