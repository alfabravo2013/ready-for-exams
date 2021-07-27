package com.github.alfabravo2013.readyforexams.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.create.SaveChangesUseCase
import kotlinx.coroutines.launch

class SaveChangesDialogViewModel(
    private val saveChangesUseCase: SaveChangesUseCase
) : ViewModel() {

    fun onPositiveButtonClick() = viewModelScope.launch {
        saveChangesUseCase.saveChanges()
    }

    fun onNegativeButtonClick() {
        saveChangesUseCase.discardChanges()
    }
}
