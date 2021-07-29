package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.util.Result

class ChecklistLocalDataSource {
    private val checklists = mutableMapOf<String, List<Task>>()
    private val createdTasks = mutableListOf<Task>()

    private var unsavedChecklist: Checklist? = null

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

    fun storeUnsavedChecklist(checklistName: String) {
        unsavedChecklist = Checklist(checklistName, createdTasks)
    }

    fun discardUnsavedChecklist() {
        unsavedChecklist = null
        clearCreatedTasks()
    }
}
