package com.github.alfabravo2013.readyforexams.presentation.create

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException

class SaveChangesDialogFragment : DialogFragment() {
    private lateinit var listener: SaveChangesDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Unsaved changes")
            .setMessage("Do you want to save all the unsaved changes?")
            .setPositiveButton("Yes") { _, _ ->
                listener.onPositiveButtonClick(this)
            }
            .setNegativeButton("No") { _, _ ->
                listener.onNegativeButtonClick(this)
            }
            .setNeutralButton("Cancel") { _, _ ->
                listener.onNeutralButtonClick(this)
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as SaveChangesDialogListener
        } catch (ex: ClassCastException) {
            throw ClassCastException("$context must implement ${this::class.simpleName}")
        }
    }

    interface SaveChangesDialogListener {
        fun onPositiveButtonClick(dialog: DialogFragment)
        fun onNegativeButtonClick(dialog: DialogFragment)
        fun onNeutralButtonClick(dialog: DialogFragment)
    }
}
