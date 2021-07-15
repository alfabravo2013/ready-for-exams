package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.toChecklistRepresentation
import com.github.alfabravo2013.readyforexams.presentation.models.ChecklistRepresentation
import com.github.alfabravo2013.readyforexams.util.Result

class ChecklistRepository(private val checklistLocalDataSource: ChecklistLocalDataSource) {
    fun getChecklists(): List<ChecklistRepresentation> =
        checklistLocalDataSource.getChecklists().map { checklist ->
            checklist.toChecklistRepresentation()
        }

    fun addChecklist(checklist: Checklist): Result =
        checklistLocalDataSource.addChecklist(checklist)

    fun updateChecklist(checklist: Checklist): Result =
        checklistLocalDataSource.updateChecklist(checklist)

    fun deleteChecklistByName(checklistName: String) =
        checklistLocalDataSource.deleteChecklistByName(checklistName)

    fun isNameTaken(name: String): Boolean = checklistLocalDataSource.isNameTaken(name)

    fun createUniqueName(): String = checklistLocalDataSource.createUniqueName()
}
