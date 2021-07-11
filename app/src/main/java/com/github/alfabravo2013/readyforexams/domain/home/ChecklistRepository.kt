package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.util.Result

class ChecklistRepository(private val checklistLocalDataSource: ChecklistLocalDataSource) {
    fun getChecklists(): List<Checklist> = checklistLocalDataSource.getChecklists()

    fun addChecklist(checklist: Checklist): Result =
        checklistLocalDataSource.addChecklist(checklist)

    fun updateChecklist(checklist: Checklist): Result =
        checklistLocalDataSource.updateChecklist(checklist)

    fun deleteChecklistByName(checklistName: String) =
        checklistLocalDataSource.deleteChecklistByName(checklistName)
}
