package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.util.Result
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class ChecklistLocalDataSourceTest {
    private val checklistLocalDataSource = ChecklistLocalDataSource()
    private val checklist = Checklist("name", listOf())

    @Nested
    @DisplayName("When getChecklists")
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

    @Nested
    @DisplayName("createdTasks")
    inner class CreatedTasksTest {
        private val task = Task("description")

        @Test
        @DisplayName("Given no tasks added, When getCreatedTasks, Then return empty list")
        fun getCreatedTasksEmpty() {
            val createdTasks = checklistLocalDataSource.getCreatedTasks()

            assertTrue(createdTasks.isEmpty())
        }

        @Test
        @DisplayName("Given one task added, When getCreatedTasks, Then return a list with a single created task")
        fun getCreatedTasksSingle() {
            checklistLocalDataSource.addCreatedTask(task)

            val createdTasks = checklistLocalDataSource.getCreatedTasks()

            assertEquals(1, createdTasks.size)
        }

        @Test
        @DisplayName("Given one task added, When getCreatedTasks, Then return a list containing the added task")
        fun getCreatedTasksSame() {
            checklistLocalDataSource.addCreatedTask(task)

            val createdTasks = checklistLocalDataSource.getCreatedTasks()

            assertEquals(task, createdTasks.first())
        }

        @Test
        @DisplayName("Given non-empty createdTasks, When clearCreatedTasks, Then return empty list")
        fun clearCreatedTasks() {
            checklistLocalDataSource.addCreatedTask(task)
            checklistLocalDataSource.clearCreatedTasks()

            val createdTasks = checklistLocalDataSource.getCreatedTasks()

            assertTrue(createdTasks.isEmpty())
        }
    }

    @Nested
    @DisplayName("EditedChecklist")
    inner class EditedChecklistTest {

        @Test
        @DisplayName("Given no EditedChecklist created, When getEditedChecklist, Then return null")
        fun getEditedChecklistNull() {
            val editedChecklist = checklistLocalDataSource.getEditedChecklist()

            assertNull(editedChecklist)
        }

        @Test
        @DisplayName("Given EditedChecklist is stored, When getEditedChecklist, Then return non-null Checklist")
        fun getEditedChecklistNonNull() {
            checklistLocalDataSource.storeEditedChecklist()

            val editedChecklist = checklistLocalDataSource.getEditedChecklist()

            assertNotNull(editedChecklist)
        }

        @Test
        @DisplayName("Given no EditedChecklist is stored, When saveEditedChecklist, Then no Checklist is added")
        fun saveEditedChecklistNull() {
            checklistLocalDataSource.saveEditedChecklist()

            val checklists = checklistLocalDataSource.getChecklists()

            assertTrue(checklists.isEmpty())
        }

        @Test
        @DisplayName("Given EditedChecklist was stored, When saveEditedChecklist, Then that Checklist is added")
        fun saveEditedChecklistNonNull() {
            checklistLocalDataSource.storeEditedChecklist()
            checklistLocalDataSource.saveEditedChecklist()

            val checklists = checklistLocalDataSource.getChecklists()

            assertEquals(1, checklists.size)
        }

        @Test
        @DisplayName("Given EditedChecklist was discarded, When getEditedChecklist, Then return null")
        fun discardEditedChecklist() {
            checklistLocalDataSource.storeEditedChecklist()
            checklistLocalDataSource.discardEditedChecklist()

            val editedChecklist = checklistLocalDataSource.getEditedChecklist()

            assertNull(editedChecklist)
        }

        @Test
        @DisplayName("Given non-empty createdTasks, When storeEditedChecklist, Then editedChecklist has the given tasks")
        fun storeEditedChecklistNonEmptyTasks() {
            val task = Task("description")
            checklistLocalDataSource.addCreatedTask(task)
            checklistLocalDataSource.storeEditedChecklist()

            val createdTasks = checklistLocalDataSource.getCreatedTasks()
            val storedTasks = checklistLocalDataSource.getEditedChecklist()?.tasks ?: emptyList()

            assertContentEquals(createdTasks, storedTasks)
        }
    }

    @Nested
    @DisplayName("editedChecklistName")
    inner class EditedChecklistNameTest {

        @Test
        @DisplayName("Given editedChecklistName hasn't been changed, When getEditedChecklistName, Then return empty string")
        fun getEditedChecklistNameEmpty() {
            val name = checklistLocalDataSource.getEditedChecklistName()

            assertTrue(name.isEmpty())
        }

        @Test
        @DisplayName("Given editedChecklistName is set, When getEditedChecklistName, Then return the set string")
        fun getEditedChecklistNameAsSet() {
            val name = "name"
            checklistLocalDataSource.setEditedChecklistName(name)

            val editedChecklistName = checklistLocalDataSource.getEditedChecklistName()

            assertEquals(name, editedChecklistName)
        }

        @Test
        @DisplayName("Given any editedChecklistName, When storeEditedChecklist, Then editedChecklist has the given name")
        fun setEditedChecklistNameStored() {
            val name = "name"
            checklistLocalDataSource.setEditedChecklistName(name)
            checklistLocalDataSource.storeEditedChecklist()

            val editedChecklist = checklistLocalDataSource.getEditedChecklist()

            assertEquals(name, editedChecklist?.name)
        }
    }

    @Nested
    @DisplayName("editedTaskDescription")
    inner class EditedTaskDescriptionTest {

        @Test
        @DisplayName("Given editedTaskDescription was not changed, When getEditedTaskDescription, Then return empty string")
        fun getEditedTaskDescriptionEmpty() {
            val description = checklistLocalDataSource.getEditedTaskDescription()

            assertTrue(description.isEmpty())
        }

        @Test
        @DisplayName("Given editedTaskDescription was set, When getEditedTaskDescription, Then return the same string")
        fun getEditedTaskDescriptionSame() {
            val description = "description"
            checklistLocalDataSource.setEditedTaskDescription(description)

            val editedTaskDescription = checklistLocalDataSource.getEditedTaskDescription()

            assertEquals(description, editedTaskDescription)
        }
    }

    @Nested
    @DisplayName("Internal State")
    inner class InternalStateTest {

        @Test
        @DisplayName("Given any case, When initialized, Then editable state is clean")
        fun initialize() {
            assertTrue(isStateClean())
        }

        @Test
        @DisplayName("Given any case, When saveEditedChecklist, Then editable state is clean")
        fun cleanStateOnSaveEditedChecklist() {
            makeStateDirty()

            checklistLocalDataSource.saveEditedChecklist()

            assertTrue(isStateClean())
        }

        @Test
        @DisplayName("Given any case, When discardEditedChecklist, Then editable state is clean")
        fun cleanStateOnDiscardEditedChecklist() {
            makeStateDirty()

            checklistLocalDataSource.discardEditedChecklist()

            assertTrue(isStateClean())
        }

        private fun isStateClean(): Boolean {
            return with(checklistLocalDataSource) {
                getEditedChecklistName().isEmpty() &&
                        getEditedTaskDescription().isEmpty() &&
                        getCreatedTasks().isEmpty() &&
                        getEditedChecklist() == null
            }
        }

        private fun makeStateDirty() {
            with(checklistLocalDataSource) {
                setEditedTaskDescription("description")
                setEditedChecklistName("name")
            }
        }
    }
}
