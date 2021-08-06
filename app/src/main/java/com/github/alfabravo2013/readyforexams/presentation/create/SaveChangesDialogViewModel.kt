package com.github.alfabravo2013.readyforexams.presentation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.create.SaveChangesUseCase
import kotlinx.coroutines.launch

class SaveChangesDialogViewModel(
    private val saveChangesUseCase: SaveChangesUseCase
) : ViewModel() {

    private val _onEvent = MutableLiveData<OnEvent>()
    val onEvent: LiveData<OnEvent> get() = _onEvent

    fun onPositiveButtonClick() = viewModelScope.launch {
        saveChangesUseCase.saveChanges()
        _onEvent.value = OnEvent.NavigateToHomeScreen
    }

    fun onNegativeButtonClick() = viewModelScope.launch {
        saveChangesUseCase.discardChanges()
        _onEvent.value = OnEvent.NavigateToHomeScreen
    }

    sealed class OnEvent {
        object NavigateToHomeScreen : OnEvent()
    }
}
