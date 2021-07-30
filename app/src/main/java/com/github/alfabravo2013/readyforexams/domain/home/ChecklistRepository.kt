package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.domain.models.toChecklistRepresentation
import com.github.alfabravo2013.readyforexams.domain.models.toTaskRepresentation
import com.github.alfabravo2013.readyforexams.presentation.models.ChecklistRepresentation
import com.github.alfabravo2013.readyforexams.presentation.models.TaskRepresentation
import com.github.alfabravo2013.readyforexams.util.Result

class ChecklistRepository(private val checklistLocalDataSource: ChecklistLocalDataSource) {
    fun getChecklists(): List<ChecklistRepresentation> =
        checklistLocalDataSource.getChecklists().map { checklist ->
            checklist.toChecklistRepresentation()
        }

    fun addChecklist(name: String): Result {
        val tasks = checklistLocalDataSource.getCreatedTasks()
        val checklist = Checklist(name, tasks)
        val result = checklistLocalDataSource.addChecklist(checklist)

        if (result is Result.Success) {
            checklistLocalDataSource.clearCreatedTasks()
        }

        return result
    }

    fun updateChecklist(checklist: Checklist): Result =
        checklistLocalDataSource.updateChecklist(checklist)

    fun deleteChecklistByName(checklistName: String) =
        checklistLocalDataSource.deleteChecklistByName(checklistName)

    fun addTask(description: String) {
        val task = Task(description)
        checklistLocalDataSource.addCreatedTask(task)
    }

    fun getCreatedTasks(): List<TaskRepresentation> =
        checklistLocalDataSource.getCreatedTasks().map { task ->
            task.toTaskRepresentation()
        }

    fun getEditedChecklist() = checklistLocalDataSource.getEditedChecklist()

    fun getEditedChecklistName() = checklistLocalDataSource.getEditedChecklistName()

    fun getEditedTaskDescription() = checklistLocalDataSource.getEditedTaskDescription()

    fun setEditedChecklistName(name: String) =
        checklistLocalDataSource.setEditedChecklistName(name)

    fun setEditedTaskDescription(description: String) =
        checklistLocalDataSource.setEditedTaskDescription(description)

    fun storeEditedChecklist() = checklistLocalDataSource.storeEditedChecklist()

    fun discardEditedChecklist() = checklistLocalDataSource.discardEditedChecklist()
}
