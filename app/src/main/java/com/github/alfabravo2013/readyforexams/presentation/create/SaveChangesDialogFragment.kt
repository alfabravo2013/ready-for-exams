package com.github.alfabravo2013.readyforexams.presentation.create

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.presentation.create.SaveChangesDialogViewModel.OnEvent
import org.koin.android.ext.android.inject

class SaveChangesDialogFragment : DialogFragment() {
    private val viewModel: SaveChangesDialogViewModel by inject()

    private val onEventObserver = Observer<OnEvent> { event ->
        when (event) {
            is OnEvent.NavigateToHomeScreen -> navigateToHomeScreen()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewModel.onEvent.observe(this, onEventObserver)

        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.save_changes_dialog_title))
            .setMessage(getString(R.string.save_changes_dialog_message))
            .setPositiveButton(getString(R.string.save_changes_dialog_positive)) { _, _ ->
                viewModel.onPositiveButtonClick()
            }
            .setNegativeButton(getString(R.string.save_changes_dialog_nagative)) { _, _ ->
                viewModel.onNegativeButtonClick()
            }
            .setNeutralButton(getString(R.string.save_changes_dialog_neutral)) { _, _ ->
                dismiss()
            }
            .create()
    }

    override fun onResume() {
        super.onResume()
        this.showsDialog = true
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_saveChangesDialogFragment_to_homeFragment)
    }
}
