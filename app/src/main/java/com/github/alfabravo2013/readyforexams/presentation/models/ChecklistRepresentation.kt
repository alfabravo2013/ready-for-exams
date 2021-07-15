package com.github.alfabravo2013.readyforexams.presentation.models

data class ChecklistRepresentation(
    val name: String,
    val completed: Int,
    val total: Int,
    val taskCountResource: Int,
    val statusTextResource: Int,
    val statusColorResource: Int
)
