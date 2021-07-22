package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository

class SaveChangesUseCase(private val checklistRepository: ChecklistRepository) {

    fun isSaveChangesRequired(): Boolean = checklistRepository.getCreatedTasks().isNotEmpty()
}
