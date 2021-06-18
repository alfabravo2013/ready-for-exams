package com.github.alfabravo2013.readyforexams.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentToolbarBinding

class ToolbarFragment : Fragment(R.layout.fragment_toolbar) {
    companion object {
        private const val TITLE_KEY = "TOOLBAR_TITLE"
        fun getInstance(title: String) : ToolbarFragment {
            val arg = Bundle().apply { putString(TITLE_KEY, title) }
            val toolbar = ToolbarFragment()
            toolbar.arguments = arg
            return toolbar
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentToolbarBinding.bind(view)
        binding.toolbarTitle.text = arguments?.getString(TITLE_KEY, "") ?: ""
    }
}