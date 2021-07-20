package com.github.alfabravo2013.readyforexams.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.create.AddTaskUseCase
import com.github.alfabravo2013.readyforexams.domain.create.CreateChecklistUseCase
import com.github.alfabravo2013.readyforexams.domain.create.GetCreatedTasksUseCase
import com.github.alfabravo2013.readyforexams.presentation.models.TaskRepresentation
import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import kotlinx.coroutines.launch

class CreateViewModel(
    private val createChecklistUseCase: CreateChecklistUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val getCreatedTasksUseCase: GetCreatedTasksUseCase
) : ViewModel() {

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    fun onAddTaskButtonClick(description: String) = viewModelScope.launch {
        when (val result = addTaskUseCase.addTask(description)) {
            is Result.Failure -> _onEvent.value = OnEvent.Error(result.errorMessage)
            is Result.Success -> {
                val taskRepresentations = getCreatedTasksUseCase.getCreatedTasks()
                _onEvent.value = OnEvent.LoadedTasks(taskRepresentations)
            }
        }
    }

    fun onCreateButtonClick(name: String) = viewModelScope.launch {
        when (val result = createChecklistUseCase.createChecklist(name)) {
            is Result.Success -> _onEvent.value = OnEvent.CreateChecklistSuccess
            is Result.Failure -> _onEvent.value = OnEvent.Error(result.errorMessage)
        }
    }

    fun onUpButtonClick() {

    }

    sealed class OnEvent {
        data class LoadedTasks(val taskRepresentations: List<TaskRepresentation>) : OnEvent()
        object CreateChecklistSuccess : OnEvent()
        object ShowUnsavedChangesDialog : OnEvent()
        data class Error(val errorMessage: String = "") : OnEvent()
    }
}
