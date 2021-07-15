package com.github.alfabravo2013.readyforexams.domain.home

import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.domain.models.toChecklistRepresentation
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class LoadChecklistUseCaseTest {
    private val checklistRepository = mockk<ChecklistRepository>()
    private val loadChecklistUseCase = LoadChecklistUseCase(checklistRepository)

    @Test
    @DisplayName("Given no checklists have been added yet, When getChecklists(), Then return empty list")
    fun getChecklistsEmptyList() {
        every { checklistRepository.getChecklists() } answers { emptyList() }

        val checklists = loadChecklistUseCase.getChecklists()

        assertEquals(0, checklists.size)

        verify(exactly = 1) { checklistRepository.getChecklists() }
    }

    @Test
    @DisplayName("When 1 checklist has been added, Then return a list size == 1")
    fun getChecklistsNonEmpty() {
        val checklist = Checklist("name", listOf())
        val checklistRepresentation = checklist.toChecklistRepresentation()

        every { checklistRepository.getChecklists() } answers { listOf(checklistRepresentation) }

        val checklists = loadChecklistUseCase.getChecklists()

        assertEquals(1, checklists.size)

        verify(exactly = 1) { checklistRepository.getChecklists() }
    }

    @Test
    @DisplayName("When a checklist have been added, Then return a list contains this checklist")
    fun getChecklistsContainsAddedChecklists() {
        val checklist = Checklist("name", listOf())
        val checklistRepresentation = checklist.toChecklistRepresentation()

        every { checklistRepository.getChecklists() } answers { listOf(checklistRepresentation) }

        val retrievedChecklists = checklistRepository.getChecklists()

        assertEquals(listOf(checklistRepresentation), retrievedChecklists)

        verify(exactly = 1) { checklistRepository.getChecklists() }
    }
}
