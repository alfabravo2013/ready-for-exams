package com.github.alfabravo2013.readyforexams.presentation.create

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import org.koin.android.ext.android.inject

class SaveChangesDialogFragment : DialogFragment() {
    private val viewModel: SaveChangesDialogViewModel by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Unsaved changes")
            .setMessage("Any unsaved changes will be discarded. Do you want to save them?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.onPositiveButtonClick()
            }
            .setNegativeButton("No") { _, _ ->
                viewModel.onNegativeButtonClick()
                navigateToHomeScreen()
            }
            .create()
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_saveChangesDialogFragment_to_homeFragment)
    }

    override fun onResume() {
        super.onResume()
        this.showsDialog = true
    }
}
