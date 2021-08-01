package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository

class CheckUnsavedChangesUseCase(private val checklistRepository: ChecklistRepository) {

    fun isSaveChangesRequired(): Boolean {
        val editedChecklistName = checklistRepository.getEditedChecklistName()
        val createdTasks = checklistRepository.getCreatedTasks()

        if (editedChecklistName.isBlank() && createdTasks.isEmpty()) {
            checklistRepository.discardEditedChecklist()
            return false
        }

        checklistRepository.storeEditedChecklist()
        return true
    }
}
