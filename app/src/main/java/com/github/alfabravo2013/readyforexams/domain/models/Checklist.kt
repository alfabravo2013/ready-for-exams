package com.github.alfabravo2013.readyforexams.domain.models

import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.presentation.models.ChecklistRepresentation

data class Checklist(val name: String, val tasks: List<Task>) {
    val total: Int = tasks.size
    val completed: Int = tasks.count { it.isCompleted }
}

fun Checklist.toChecklistRepresentation(): ChecklistRepresentation {
    return ChecklistRepresentation(
        name = this.name,
        completed = this.completed,
        total = this.total,
        taskCountResource = R.string.home_checklist_tasks_count_text,
        statusTextResource = when (this.completed) {
            this.total -> R.string.home_checklist_done_text
            0 -> R.string.home_checklist_not_started_text
            else -> R.string.home_checklist_in_progress_text
        },
        statusColorResource = when (this.completed) {
            this.total -> R.color.green
            0 -> R.color.red_brick
            else -> R.color.font_gray
        }
    )
}
