package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.util.Result
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ChecklistLocalDataSourceTest {
    private val checklistLocalDataSource = ChecklistLocalDataSource()
    private val checklist = Checklist("name", listOf())

    @Nested
    @DisplayName("When getChecklist")
    inner class GetChecklistsTest {

        @Test
        @DisplayName("Given no checklists have been added yet, Then return empty list")
        fun getChecklistsWhereNoHasBeenAddedYet() {
            val checklists = checklistLocalDataSource.getChecklists()

            assertEquals(0, checklists.size)
        }

        @Test
        @DisplayName("Given a checklist has been added, Then return a list containing the single checklist")
        fun getChecklistsWhereOneChecklistHasBeenAdded() {
            checklistLocalDataSource.addChecklist(checklist)

            val checklists = checklistLocalDataSource.getChecklists()

            assertEquals(1, checklists.size)
        }

        @Test
        @DisplayName("Given a checklist has been added, Then the returned list contains only the added checklist")
        fun getChecklistsShouldContainTheAddedChecklist() {
            checklistLocalDataSource.addChecklist(checklist)

            val checklists = checklistLocalDataSource.getChecklists()

            assertEquals(checklist, checklists.first())
        }
    }

    @Nested
    @DisplayName("When addChecklist")
    inner class AddChecklistTest {

        @Test
        @DisplayName("Given a unique checklist name Then return Result.Success")
        fun addChecklistWithUniqueName() {
            val result = checklistLocalDataSource.addChecklist(checklist)

            assertTrue(result is Result.Success)
        }

        @Test
        @DisplayName("Given a non-unique checklist name Then return Result.Failure")
        fun addChecklistWithNonUniqueName() {
            checklistLocalDataSource.addChecklist(checklist)

            val result = checklistLocalDataSource.addChecklist(checklist)

            assertTrue(result is Result.Failure)
        }

        @Test
        @DisplayName("Given a unique checklist name Then the ChecklistLocalDataSource contains the added checklist")
        fun addChecklistAddsTheChecklist() {
            checklistLocalDataSource.addChecklist(checklist)

            val result = checklistLocalDataSource.getChecklists().first()

            assertEquals(checklist, result)
        }
    }

    @Nested
    @DisplayName("When updateChecklist")
    inner class UpdateChecklistTest {

        @Test
        @DisplayName("Given an existing checklist name, Then return Result.Success")
        fun updateExistingChecklist() {
            checklistLocalDataSource.addChecklist(checklist)

            val result = checklistLocalDataSource.updateChecklist(checklist)

            assertTrue(result is Result.Success)
        }

        @Test
        @DisplayName("Given an existing checklist name, Then the checklist is updated")
        fun updateExistingChecklistWithNewData() {
            val modifiedChecklist = Checklist("name", listOf(Task("task")))

            checklistLocalDataSource.addChecklist(checklist)
            checklistLocalDataSource.updateChecklist(modifiedChecklist)

            val retrievedChecklist = checklistLocalDataSource.getChecklists().first()

            assertEquals(modifiedChecklist, retrievedChecklist)
        }

        @Test
        @DisplayName("Given a non-existing checklist, Then return Result.Failure")
        fun updateNonExistingChecklist() {
            val nonExistingChecklist = Checklist("another", listOf())

            checklistLocalDataSource.addChecklist(checklist)

            val result = checklistLocalDataSource.updateChecklist(nonExistingChecklist)

            assertTrue(result is Result.Failure)
        }

        @Test
        @DisplayName("Given a non-existing checklist, Then no checklist is updated")
        fun updateNonExistingChecklistWithNewData() {
            val nonExistingChecklist = Checklist("another", listOf(Task("task")))

            checklistLocalDataSource.addChecklist(checklist)
            checklistLocalDataSource.updateChecklist(nonExistingChecklist)

            val retrievedChecklist = checklistLocalDataSource.getChecklists().first()

            assertEquals(checklist, retrievedChecklist)
        }
    }

    @Nested
    @DisplayName("When deleteChecklistByName")
    inner class DeleteChecklistByNameTest {

        @Test
        @DisplayName("Given an existing checklist name, Then the checklist is deleted")
        fun deleteExistingChecklist() {
            checklistLocalDataSource.addChecklist(checklist)

            checklistLocalDataSource.deleteChecklistByName("name")

            val checklists = checklistLocalDataSource.getChecklists()

            assertEquals(0, checklists.size)
        }

        @Test
        @DisplayName("Given a non-existing checklist name, Then no checklist is deleted")
        fun deleteNonExistingChecklist() {
            checklistLocalDataSource.addChecklist(checklist)

            checklistLocalDataSource.deleteChecklistByName("another")

            val checklists = checklistLocalDataSource.getChecklists()

            assertEquals(1, checklists.size)
        }
    }
}
