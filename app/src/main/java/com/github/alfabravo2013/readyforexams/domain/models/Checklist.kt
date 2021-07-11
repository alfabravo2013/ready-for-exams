package com.github.alfabravo2013.readyforexams.domain.models

data class Checklist(val name: String, val tasks: List<Task>) {
    val total: Int = tasks.size
    val completed: Int = tasks.count { it.isCompleted }
}
