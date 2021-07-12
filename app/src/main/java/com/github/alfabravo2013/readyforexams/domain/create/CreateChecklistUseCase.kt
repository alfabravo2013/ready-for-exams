package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.util.Result

class CreateChecklistUseCase(private val checklistRepository: ChecklistRepository) {
    fun createChecklist(name: String, tasks: List<Task>): Result {
        if (name.isBlank()) {
            val uniqueName = checklistRepository.createUniqueName()
            return checklistRepository.addChecklist(Checklist(uniqueName, tasks))
        }

        if (checklistRepository.isNameTaken(name)) {
            return Result.Failure("Name $name is already taken")
        }

        return checklistRepository.addChecklist(Checklist(name, tasks))
    }
}
