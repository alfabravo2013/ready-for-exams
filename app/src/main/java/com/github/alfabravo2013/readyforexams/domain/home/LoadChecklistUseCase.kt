package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.toChecklistRepresentation
import com.github.alfabravo2013.readyforexams.presentation.models.ChecklistRepresentation

class LoadChecklistUseCase(private val checklistRepository: ChecklistRepository) {
    fun getChecklists(): List<ChecklistRepresentation> =
        checklistRepository.getChecklists().map { checklist ->
            checklist.toChecklistRepresentation()
        }
}
