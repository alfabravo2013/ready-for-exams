package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.util.Result

class SaveChangesUseCase(private val checklistRepository: ChecklistRepository) {
    fun saveChanges(): Result {
        return checklistRepository.saveUnsavedChecklist()
    }

    fun discardChanges() {
        checklistRepository.discardUnsavedChecklist()
    }
}
