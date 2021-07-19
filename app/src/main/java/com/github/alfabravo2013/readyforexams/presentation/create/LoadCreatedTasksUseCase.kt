package com.github.alfabravo2013.readyforexams.presentation.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.presentation.models.TaskRepresentation

class LoadCreatedTasksUseCase(private val checklistRepository: ChecklistRepository) {

    fun getCreatedTasks(): List<TaskRepresentation> = checklistRepository.getCreatedTasks()
}
