package com.github.alfabravo2013.readyforexams.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.alfabravo2013.readyforexams.domain.home.LoadChecklistUseCase
import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent

class HomeViewModel(
    private val loadChecklistUseCase: LoadChecklistUseCase
) : ViewModel() {
    private val _checklists = MutableLiveData<List<Checklist>>()
    val checklists: LiveData<List<Checklist>> get() = _checklists

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    init {
        fetchChecklists()
    }

    fun onAddChecklistButtonClick() {
        _onEvent.value = OnEvent.NavigateToCreateScreen
    }

    private fun fetchChecklists() {
        _checklists.value = loadChecklistUseCase.getChecklists()
        _onEvent.value = if (_checklists.value.isNullOrEmpty()) {
            OnEvent.EmptyList
        } else {
            OnEvent.NotEmptyList
        }
    }

    sealed class OnEvent {
        object EmptyList : OnEvent()
        object NotEmptyList : OnEvent()
        object NavigateToCreateScreen : OnEvent()
    }
}
