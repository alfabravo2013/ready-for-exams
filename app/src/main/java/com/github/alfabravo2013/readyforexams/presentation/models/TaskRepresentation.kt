package com.github.alfabravo2013.readyforexams.presentation.models

import com.github.alfabravo2013.readyforexams.domain.models.Task

data class TaskRepresentation(val description: String)

fun TaskRepresentation.toTask(isCompleted: Boolean = false): Task {
    return Task(
        description = this.description,
        isCompleted = isCompleted
    )
}
