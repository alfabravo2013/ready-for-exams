package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.toChecklistRepresentation
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

    private val checklist = Checklist("name", listOf())
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
        private val name = "name"
        private val taskRepresentations = listOf<TaskRepresentation>()

        @Test
        @DisplayName("Given a unique checklist name Then return Result.Success")
        fun addChecklistWithUniqueName() {
            every { checklistLocalDataSource.addChecklist(checklist) } returns Result.Success

            val result = checklistRepository.addChecklist(name)

            assertTrue(result is Result.Success)
            verify(exactly = 1) { checklistLocalDataSource.addChecklist(checklist) }
        }

        @Test
        @DisplayName("Given a non-unique checklist name Then return Result.Failure")
        fun addChecklistWithNonUniqueName() {
            every { checklistLocalDataSource.addChecklist(checklist) } returns Result.Failure()

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
        @DisplayName("given any checklist name, Then call ChecklistLocalDataSource.deleteChecklistByName with this name")
        fun deleteChecklistByName() {
            every { checklistLocalDataSource.deleteChecklistByName(any()) } just Runs

            checklistRepository.deleteChecklistByName("name")

            verify(exactly = 1) { checklistLocalDataSource.deleteChecklistByName("name") }
        }
    }
}
