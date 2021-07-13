package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.util.Result

class ChecklistLocalDataSource {
    private val checklists = mutableMapOf<String, List<Task>>()

    fun getChecklists(): List<Checklist> = checklists.entries.map { entry ->
        Checklist(entry.key, entry.value)
    }

    fun isNameTaken(name: String): Boolean = checklists.containsKey(name)

    fun createUniqueName(): String {
        val newNumber = checklists.keys.asSequence()
            .filter { name -> name.matches(Regex("New task #\\d+")) }
            .map { name -> name.substring(name.lastIndexOf('#') + 1) }
            .map { number -> number.toInt() }
            .maxOrNull()
            ?.plus(1) ?: 1

        return "New task #$newNumber"
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
}
