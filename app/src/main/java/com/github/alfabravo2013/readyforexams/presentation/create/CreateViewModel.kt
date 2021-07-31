package com.github.alfabravo2013.readyforexams.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.create.AddTaskUseCase
import com.github.alfabravo2013.readyforexams.domain.create.CreateChecklistUseCase
import com.github.alfabravo2013.readyforexams.domain.create.GetCreatedTasksUseCase
import com.github.alfabravo2013.readyforexams.domain.create.CheckUnsavedChangesUseCase
import com.github.alfabravo2013.readyforexams.domain.create.UpdateEditedDataUseCase
import com.github.alfabravo2013.readyforexams.presentation.models.TaskRepresentation
import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import kotlinx.coroutines.launch

class CreateViewModel(
    private val createChecklistUseCase: CreateChecklistUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val getCreatedTasksUseCase: GetCreatedTasksUseCase,
    private val checkUnsavedChangesUseCase: CheckUnsavedChangesUseCase,
    private val updateEditedDataUseCase: UpdateEditedDataUseCase
) : ViewModel() {

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    fun onAddTaskButtonClick() = viewModelScope.launch {
        when (val result = addTaskUseCase.addTask()) {
            is Result.Failure -> _onEvent.value = OnEvent.Error(result.errorMessage)
            is Result.Success -> {
                val taskRepresentations = getCreatedTasksUseCase.getCreatedTasks()
                _onEvent.value = OnEvent.LoadedTasks(taskRepresentations)
            }
        }
    }

    fun onCreateButtonClick() = viewModelScope.launch {
        when (val result = createChecklistUseCase.createChecklist()) {
            is Result.Success -> {
                _onEvent.value = OnEvent.ChecklistCreatedMessage
                _onEvent.value = OnEvent.CreateChecklistSuccess
            }
            is Result.Failure -> _onEvent.value = OnEvent.Error(result.errorMessage)
        }
    }

    fun onUpButtonClick() = viewModelScope.launch {
        if (checkUnsavedChangesUseCase.isSaveChangesRequired()) {
            _onEvent.value = OnEvent.ShowUnsavedChangesDialog
        } else {
            _onEvent.value = OnEvent.NavigateToHomeScreen
        }
    }

    fun updateCurrentChecklistName(checklistName: String) = viewModelScope.launch {
        updateEditedDataUseCase.setEditedChecklistName(checklistName)
    }

    fun updateCurrentTaskDescription(taskDescription: String) = viewModelScope.launch {
        updateEditedDataUseCase.setEditedTaskDescription(taskDescription)
    }

    sealed class OnEvent {
        data class LoadedTasks(val taskRepresentations: List<TaskRepresentation>) : OnEvent()
        object CreateChecklistSuccess : OnEvent()
        object ShowUnsavedChangesDialog : OnEvent()
        object NavigateToHomeScreen : OnEvent()
        object ChecklistCreatedMessage : OnEvent()
        data class Error(val errorMessage: String = "") : OnEvent()
    }
}
