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

class CreateFragment : BaseFragment(R.layout.fragment_create) {
    private val viewModel: CreateViewModel by viewModel()
    private lateinit var binding: FragmentCreateBinding

    private val adapter: CreateTaskAdapter by lazy { CreateTaskAdapter() }

    private val onEventObserver = Observer<OnEvent> { event ->
        when (event) {
            is OnEvent.LoadedTasks -> adapter.setItems(event.taskRepresentations)
            is OnEvent.CreateChecklistSuccess -> navigateToHomeScreen()
            is OnEvent.Error -> showMessage(event.errorMessage)
            is OnEvent.ShowUnsavedChangesDialog -> {}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreateBinding.bind(view)
        setToolbarTitle(requireContext().getString(R.string.create_title_text))

        binding.tasksRecyclerView.adapter = adapter

        with (binding) {
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

    private fun navigateToHomeScreen() {
        showMessage("You have successfully created a new checklist")
        findNavController().popBackStack()
    }
}
