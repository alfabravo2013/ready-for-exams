package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.util.Result

class ChecklistLocalDataSource {
    private val checklists = mutableMapOf<String, List<Task>>()

    private val createdTasks = mutableListOf<Task>()

    private var editedChecklistName: String = ""
    private var editedTaskDescription: String = ""

    private var editedChecklist: Checklist? = null

    fun setEditedChecklistName(name: String) {
        editedChecklistName = name
    }

    fun setEditedTaskDescription(description: String) {
        editedTaskDescription = description
    }

    fun getEditedChecklistName() = editedChecklistName

    fun getEditedTaskDescription() = editedTaskDescription

    fun getEditedChecklist(): Checklist? = editedChecklist

    fun getCreatedTasks() = createdTasks.toList()

    fun addCreatedTask(task: Task) = createdTasks.add(task)

    fun clearCreatedTasks() = createdTasks.clear()

    fun getChecklists(): List<Checklist> = checklists.entries.map { entry ->
        Checklist(entry.key, entry.value)
    }

    fun addChecklist(checklist: Checklist) : Result {
        if (checklists.containsKey(checklist.name)) {
            return Result.Failure("Checklist ${checklist.name} already exists")
        }

        checklists[checklist.name] = checklist.tasks
        return Result.Success
    }

    fun updateChecklist(checklist: Checklist) : Result {
        return if (checklists.containsKey(checklist.name)) {
            checklists[checklist.name] = checklist.tasks
            Result.Success
        } else {
            Result.Failure("Checklist ${checklist.name} not found")
        }
    }

    fun deleteChecklistByName(checklistName: String) {
        checklists.remove(checklistName)
    }

    fun saveEditedChecklist() {
        val checklist = getEditedChecklist() ?: return
        addChecklist(checklist)
    }

    fun storeEditedChecklist() {
        editedChecklist = Checklist(editedChecklistName, createdTasks)
    }

    fun discardEditedChecklist() {
        editedChecklist = null
        clearCreatedTasks()
    }
}
