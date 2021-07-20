package com.github.alfabravo2013.readyforexams.domain.create

import com.github.alfabravo2013.readyforexams.domain.home.ChecklistRepository
import com.github.alfabravo2013.readyforexams.util.Result

class AddTaskUseCase(private val checklistRepository: ChecklistRepository) {

    fun addTask(description: String): Result {
        if (description.isBlank()) {
            return Result.Failure("Please enter a description for this task")
        }

        checklistRepository.addTask(description)

        return Result.Success
    }
}
