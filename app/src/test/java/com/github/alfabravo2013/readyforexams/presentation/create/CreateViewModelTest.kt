package com.github.alfabravo2013.readyforexams.presentation.create

import androidx.lifecycle.Observer
import com.github.alfabravo2013.readyforexams.CoroutinesTestExtension
import com.github.alfabravo2013.readyforexams.InstantExecutorExtension
import com.github.alfabravo2013.readyforexams.domain.create.AddTaskUseCase
import com.github.alfabravo2013.readyforexams.domain.create.CheckUnsavedChangesUseCase
import com.github.alfabravo2013.readyforexams.domain.create.CreateChecklistUseCase
import com.github.alfabravo2013.readyforexams.domain.create.GetCreatedTasksUseCase
import com.github.alfabravo2013.readyforexams.domain.create.UpdateEditedDataUseCase
import com.github.alfabravo2013.readyforexams.domain.models.Task
import com.github.alfabravo2013.readyforexams.domain.models.toTaskRepresentation
import com.github.alfabravo2013.readyforexams.util.Result
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
internal class CreateViewModelTest {
    private val createChecklistUseCase = mockk<CreateChecklistUseCase>()
    private val addTaskUseCase = mockk<AddTaskUseCase>()
    private val getCreatedTasksUseCase = mockk<GetCreatedTasksUseCase>()
    private val checkUnsavedChangesUseCase = mockk<CheckUnsavedChangesUseCase>()
    private val updateEditedDataUseCase = mockk<UpdateEditedDataUseCase>()

    private val viewModel = CreateViewModel(
        createChecklistUseCase,
        addTaskUseCase,
        getCreatedTasksUseCase,
        checkUnsavedChangesUseCase,
        updateEditedDataUseCase
    )

    private val observer = mockk<Observer<CreateViewModel.OnEvent>>()

    @BeforeEach
    fun setup() {
        viewModel.onEvent.observeForever(observer)

        every { observer.onChanged(any()) } just Runs
    }

    @Test
    @DisplayName("Given task can be successfully added, When onAddTaskButtonClick, Then update task list")
    fun onAddTaskButtonCLickValidTask() {
        val task = Task("task")
        val list = listOf(task.toTaskRepresentation())
        every { addTaskUseCase.addTask() } returns Result.Success
        every { getCreatedTasksUseCase.getCreatedTasks() } answers { list }

        viewModel.onAddTaskButtonClick()

        verifySequence {
            addTaskUseCase.addTask()
            getCreatedTasksUseCase.getCreatedTasks()
            observer.onChanged(CreateViewModel.OnEvent.LoadedTasks(list))
        }
    }

    @Test
    @DisplayName("Given adding a task fails, When onAddTaskButtonClick, Then show error")
    fun onAddTaskButtonCLickInvalidTask() {
        val task = Task("task")
        val list = listOf(task.toTaskRepresentation())
        every { addTaskUseCase.addTask() } returns Result.Failure("error")
        every { getCreatedTasksUseCase.getCreatedTasks() } answers { list }

        viewModel.onAddTaskButtonClick()

        verifySequence {
            addTaskUseCase.addTask()
            observer.onChanged(CreateViewModel.OnEvent.Error("error"))
        }
    }

    @Test
    @DisplayName("Given a checklist can be successfully created, When onCreateButtonClick, Then set event to success")
    fun onCreateButtonClickSuccess() {
        every { createChecklistUseCase.createChecklist() } returns Result.Success

        viewModel.onCreateButtonClick()

        verifySequence {
            createChecklistUseCase.createChecklist()
            observer.onChanged(CreateViewModel.OnEvent.ChecklistCreatedMessage)
            observer.onChanged(CreateViewModel.OnEvent.CreateChecklistSuccess)
        }
    }

    @Test
    @DisplayName("Given a checklist can't be created, When onCreateButtonClick, Then display an error")
    fun onCreateButtonClickFailure() {
        every { createChecklistUseCase.createChecklist() } returns Result.Failure("error")

        viewModel.onCreateButtonClick()

        verifySequence {
            createChecklistUseCase.createChecklist()
            observer.onChanged(CreateViewModel.OnEvent.Error("error"))
        }
    }

    @Test
    @DisplayName("Given any unsaved changes, When onUpButtonClick, Then show AlertDialog")
    fun onUpButtonClickNeedSaving() {
        every { checkUnsavedChangesUseCase.isSaveChangesRequired() } returns true

        viewModel.onUpButtonClick()

        verifySequence {
            checkUnsavedChangesUseCase.isSaveChangesRequired()
            observer.onChanged(CreateViewModel.OnEvent.ShowUnsavedChangesDialog)
        }
    }

    @Test
    @DisplayName("Given no unsaved changes, When onUpButtonClick, Then navigate to HomeS creen")
    fun onUpButtonClickNoSaving() {
        every { checkUnsavedChangesUseCase.isSaveChangesRequired() } returns false

        viewModel.onUpButtonClick()

        verifySequence {
            checkUnsavedChangesUseCase.isSaveChangesRequired()
            observer.onChanged(CreateViewModel.OnEvent.NavigateToHomeScreen)
        }
    }

    @Test
    @DisplayName("Given any checklist name, When updateCurrentChecklistName, Then call use case's setEditedChecklistName with the same string")
    fun updateCurrentChecklistName() {
        val name = "name"
        every { updateEditedDataUseCase.setEditedChecklistName(name) } just Runs

        viewModel.updateCurrentChecklistName(name)

        verify(exactly = 1) { updateEditedDataUseCase.setEditedChecklistName(name) }
    }

    @Test
    @DisplayName("Given any task description, When updateCurrentTaskDescription, Then call use case's setEditedTaskDescription with the same string")
    fun updateCurrentTaskDescription() {
        val description = "description"
        every { updateEditedDataUseCase.setEditedTaskDescription(description) } just Runs

        viewModel.updateCurrentTaskDescription(description)

        verify(exactly = 1) { updateEditedDataUseCase.setEditedTaskDescription(description) }
    }
}
