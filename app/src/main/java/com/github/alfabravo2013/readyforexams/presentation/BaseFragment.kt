package com.github.alfabravo2013.readyforexams.presentation

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.alfabravo2013.readyforexams.MainActivity

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    fun setToolbarTitle(title: String = "") {
        (activity as MainActivity).setToolbarTitle(title)
    }
}