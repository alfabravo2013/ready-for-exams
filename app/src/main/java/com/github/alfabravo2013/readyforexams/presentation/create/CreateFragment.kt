package com.github.alfabravo2013.readyforexams.presentation.create

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentCreateBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.github.alfabravo2013.readyforexams.presentation.create.CreateViewModel.OnEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateFragment :
    BaseFragment<FragmentCreateBinding>(FragmentCreateBinding::inflate) {

    private val viewModel: CreateViewModel by viewModel()

    private val adapter: CreateTaskAdapter by lazy { CreateTaskAdapter() }

    private val onEventObserver = Observer<OnEvent> { event ->
        when (event) {
            is OnEvent.LoadedTasks -> adapter.setItems(event.taskRepresentations)
            is OnEvent.CreateChecklistSuccess -> navigateToHomeScreen(true)
            is OnEvent.Error -> showMessage(event.errorMessage)
            is OnEvent.ShowUnsavedChangesDialog -> showUnsavedChangesDialog()
            is OnEvent.NavigateToHomeScreen -> navigateToHomeScreen(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle(requireContext().getString(R.string.create_title_text))
        setOnNavigateUpCallback { viewModel.onUpButtonClick() }

        binding.tasksRecyclerView.adapter = adapter

        with(binding) {
            createButton.setOnClickListener {
                val name = checklistNameEditText.text.toString()
                viewModel.onCreateButtonClick(name)
            }

            addImageViewButton.setOnClickListener {
                val description = taskEditText.text.toString()
                viewModel.onAddTaskButtonClick(description)
            }
        }

        viewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHomeScreen(showSuccessMessage: Boolean) {
        if (showSuccessMessage) {
            showMessage(getString(R.string.create_checklist_created_text))
        }

        findNavController().popBackStack()
    }

    private fun showUnsavedChangesDialog() {
        // FIXME: 23.07.2021 add navigation destination and invoke the action
        showMessage("Up button clicked, not implemented yet")
    }
}
