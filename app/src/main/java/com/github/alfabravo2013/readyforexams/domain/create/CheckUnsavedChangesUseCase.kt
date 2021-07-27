package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository

class CheckUnsavedChangesUseCase(private val checklistRepository: ChecklistRepository) {

    fun isSaveChangesRequired(checklistName: String): Boolean {
        if (checklistName.isBlank() && checklistRepository.getCreatedTasks().isEmpty()) {
            checklistRepository.discardUnsavedChecklist()
            return false
        }

        checklistRepository.storeUnsavedChecklist(checklistName)
        return true
    }
}
