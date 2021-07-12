package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.util.Result

class UpdateChecklistUseCase(private val checklistRepository: ChecklistRepository) {

    fun updateChecklist(name: String, tasks: List<Task>): Result {
        val checklist = Checklist(name, tasks)
        return checklistRepository.updateChecklist(checklist)
    }
}
