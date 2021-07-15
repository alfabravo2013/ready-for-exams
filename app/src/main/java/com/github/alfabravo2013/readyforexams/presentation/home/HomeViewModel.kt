package com.github.alfabravo2013.readyforexams.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.home.LoadChecklistUseCase
import com.github.alfabravo2013.readyforexams.presentation.models.ChecklistRepresentation
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loadChecklistUseCase: LoadChecklistUseCase
) : ViewModel() {

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    fun onAddChecklistButtonClick() {
        _onEvent.value = OnEvent.NavigateToCreateScreen
    }

    fun fetchChecklists() = viewModelScope.launch {
        _onEvent.value = OnEvent.ShowProgress

        val loadedChecklists = loadChecklistUseCase.getChecklists()
        if (loadedChecklists.isEmpty()) {
            _onEvent.value = OnEvent.EmptyList
        } else {
            _onEvent.value = OnEvent.LoadChecklists(loadedChecklists)
        }

        _onEvent.value = OnEvent.HideProgress
    }

    sealed class OnEvent {
        object ShowProgress : OnEvent()
        object HideProgress : OnEvent()
        object EmptyList : OnEvent()
        object NavigateToCreateScreen : OnEvent()
        data class LoadChecklists(val checklists: List<ChecklistRepresentation>) : OnEvent()
    }
}
