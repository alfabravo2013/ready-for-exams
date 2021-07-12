package com.github.alfabravo2013.readyforexams.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alfabravo2013.readyforexams.domain.create.CreateChecklistUseCase
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.util.Result
import com.github.alfabravo2013.readyforexams.util.SingleLiveEvent
import kotlinx.coroutines.launch

class CreateViewModel(
    private val createChecklistUseCase: CreateChecklistUseCase
) : ViewModel() {
    private val tasks = mutableListOf<Task>()

    private val _onEvent = SingleLiveEvent<OnEvent>()
    val onEvent: SingleLiveEvent<OnEvent> get() = _onEvent

    // TODO: 12.07.2021 distinguish between adding task to a non-created checklist
    //  and to a checklist that has been already created, i.e. use createChecklist
    //  and updateChecklist use cases accordingly
    fun onAddTaskButtonClick(description: String) {
        tasks.add(Task(description))
        _onEvent.value = OnEvent.AddTaskSuccess
    }

    fun onCreateButtonClick(name: String) = viewModelScope.launch {
        when (val result = createChecklistUseCase.createChecklist(name, tasks)) {
            is Result.Success -> _onEvent.value = OnEvent.CreateChecklistSuccess
            is Result.Failure -> _onEvent.value = OnEvent.CreateChecklistFailure(result.errorMessage)
        }
    }

    sealed class OnEvent {
        object AddTaskSuccess : OnEvent()
        object CreateChecklistSuccess : OnEvent()
        data class CreateChecklistFailure(val message: String = "") : OnEvent()
    }
}
