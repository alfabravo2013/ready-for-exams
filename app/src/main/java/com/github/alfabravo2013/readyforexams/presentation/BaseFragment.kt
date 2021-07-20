package com.github.alfabravo2013.readyforexams.presentation

import androidx.fragment.app.Fragment
import com.github.alfabravo2013.readyforexams.MainActivity

abstract class BaseFragment : Fragment() {

    fun setToolbarTitle(title: String = "") {
        (activity as MainActivity).setToolbarTitle(title)
    }
}
