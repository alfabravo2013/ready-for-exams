package com.github.alfabravo2013.readyforexams.presentation.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentCreateBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.github.alfabravo2013.readyforexams.presentation.create.CreateViewModel.OnEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateFragment : BaseFragment<FragmentCreateBinding>() {

    private val viewModel: CreateViewModel by viewModel()

    private val adapter: CreateTaskAdapter by lazy { CreateTaskAdapter() }

    private val onEventObserver = Observer<OnEvent> { event ->
        when (event) {
            is OnEvent.LoadedTasks -> adapter.setItems(event.taskRepresentations)
            is OnEvent.CreateChecklistSuccess -> navigateToHomeScreen()
            is OnEvent.ChecklistCreatedMessage -> showChecklistCreatedMessage()
            is OnEvent.Error -> showMessage(event.errorMessage)
            is OnEvent.ShowUnsavedChangesDialog -> showUnsavedChangesDialog()
            is OnEvent.NavigateToHomeScreen -> navigateToHomeScreen()
        }
    }

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCreateBinding {
        return FragmentCreateBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle(requireContext().getString(R.string.create_title_text))
        binding.tasksRecyclerView.adapter = adapter

        with(binding) {
            createButton.setOnClickListener {
                viewModel.onCreateButtonClick()
            }

            addImageViewButton.setOnClickListener {
                viewModel.onAddTaskButtonClick()
            }

            checklistNameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.updateCurrentChecklistName(text.toString())
            }

            taskEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.updateCurrentTaskDescription(text.toString())
            }
        }

        viewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showChecklistCreatedMessage() {
        showMessage(getString(R.string.create_checklist_created_text))
    }

    private fun navigateToHomeScreen() {
        findNavController().popBackStack()
    }

    private fun showUnsavedChangesDialog() {
        showMessage("Save Change Dialog is not implemented yet")
        navigateToHomeScreen()
    }
}
