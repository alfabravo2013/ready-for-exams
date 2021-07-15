package com.github.alfabravo2013.readyforexams.domain.models

import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.presentation.models.ChecklistRepresentation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ChecklistKtTest {

    @Test
    fun checklistToChecklistRepresentationMapper() {
        val checklist = Checklist(
            "name",
            listOf(
                Task("1"),
                Task("2", isCompleted = true),
                Task("3", isCompleted = true)
            )
        )

        val expected = ChecklistRepresentation(
            name = "name",
            completed = 2,
            total = 3,
            taskCountResource = R.string.home_checklist_tasks_count_text,
            statusTextResource = R.string.home_checklist_in_progress_text,
            statusColorResource = R.color.font_gray
        )

        val actual = checklist.toChecklistRepresentation()

        assertEquals(expected, actual)
    }
}
