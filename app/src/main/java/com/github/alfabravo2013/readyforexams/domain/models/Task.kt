package com.github.alfabravo2013.readyforexams.domain.models

import com.github.alfabravo2013.readyforexams.presentation.models.TaskRepresentation

data class Task(
    val description: String,
    val isCompleted: Boolean = false
)

fun Task.toTaskRepresentation() : TaskRepresentation {
    return TaskRepresentation(
        description = this.description
    )
}
