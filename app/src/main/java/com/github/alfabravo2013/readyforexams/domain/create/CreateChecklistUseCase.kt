package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.util.Result

class CreateChecklistUseCase(private val checklistRepository: ChecklistRepository) {

    fun createChecklist(): Result {
        val checklistName = checklistRepository.getEditedChecklistName()

        if (checklistName.isBlank()) {
            return Result.Failure("Please enter the checklist name")
        }

        checklistRepository.discardEditedChecklist()
        return checklistRepository.addChecklist(checklistName)
    }
}
