package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist

class LoadChecklistUseCase(private val checklistRepository: ChecklistRepository) {
    fun getChecklists(): List<Checklist> = checklistRepository.getChecklists()
}
