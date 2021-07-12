package com.github.alfabravo2013.readyforexams.presentation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.create.CreateChecklistUseCase
import com.github.alfabravo2013.readyforexams.domain.create.UpdateChecklistUseCase
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import kotlinx.coroutines.launch

class CreateViewModel(
    private val createChecklistUseCase: CreateChecklistUseCase,
    private val updateChecklistUseCase: UpdateChecklistUseCase
) : ViewModel() {

    // TODO: 12.07.2021 move logic to use cases, including adding a new task
    private var isCreated = false
    private var checklistName = ""

    private val addedTasks = mutableListOf<Task>()
    private val _tasks = MutableLiveData<List<Task>>().apply { addedTasks.toList() }
    val tasks: LiveData<List<Task>> get() = _tasks


    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    fun onAddTaskButtonClick(description: String) = viewModelScope.launch {
        addedTasks.add(Task(description))
        _tasks.value = addedTasks.toList()
        _onEvent.value = OnEvent.AddTaskSuccess

        if (isCreated) {
            when (val result = updateChecklistUseCase.updateChecklist(checklistName, addedTasks)) {
                is Result.Success -> {
                    _onEvent.value = OnEvent.UpdateTasksSuccess
                    isCreated = true
                }
                is Result.Failure -> _onEvent.value = OnEvent.Error(result.errorMessage)
            }
        }
    }

    fun onCreateButtonClick(name: String) = viewModelScope.launch {
        when (val result = createChecklistUseCase.createChecklist(checklistName, addedTasks)) {
            is Result.Success -> {
                _onEvent.value = OnEvent.CreateChecklistSuccess
                isCreated = true
            }
            is Result.Failure -> _onEvent.value = OnEvent.Error(result.errorMessage)
        }
    }

    sealed class OnEvent {
        object AddTaskSuccess : OnEvent()
        object CreateChecklistSuccess : OnEvent()
        object UpdateTasksSuccess : OnEvent()
        data class Error(val errorMessage: String = "") : OnEvent()
    }
}
