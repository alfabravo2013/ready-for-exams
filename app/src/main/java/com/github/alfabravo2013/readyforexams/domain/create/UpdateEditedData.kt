package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository

class UpdateEditedData(private val checklistRepository: ChecklistRepository) {

    fun setEditedChecklistName(name: String) {
        if (name.isNotBlank()) {
            checklistRepository.setEditedChecklistName(name)
        }
    }

    fun setEditedTaskDescription(description: String) {
        if (description.isNotBlank()) {
            checklistRepository.setEditedTaskDescription(description)
        }
    }
}
