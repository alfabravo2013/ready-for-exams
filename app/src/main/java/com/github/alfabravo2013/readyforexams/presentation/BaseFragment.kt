package com.github.alfabravo2013.readyforexams.presentation

import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.github.alfabravo2013.readyforexams.R

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    fun enableToolbar(title: String = "") {
        requireActivity()
            .findViewById<TextView>(R.id.activity_main_toolbar_custom_title)
            .text = title

        requireActivity()
            .findViewById<Toolbar>(R.id.activity_main_toolbar)
            .visibility = View.VISIBLE
    }

    fun disableToolbar() {
        requireActivity()
            .findViewById<TextView>(R.id.activity_main_toolbar_custom_title)
            .text = ""

        requireActivity()
            .findViewById<Toolbar>(R.id.activity_main_toolbar)
            .visibility = View.GONE
    }
}