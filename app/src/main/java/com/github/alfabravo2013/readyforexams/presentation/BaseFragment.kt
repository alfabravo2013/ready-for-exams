package com.github.alfabravo2013.readyforexams.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.github.alfabravo2013.readyforexams.MainActivity

abstract class BaseFragment<BINDING : ViewBinding> : Fragment() {
    private var _binding: BINDING? = null
    val binding: BINDING get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = onCreateViewBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    abstract fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): BINDING

    fun setToolbarTitle(title: String = "") {
        (activity as MainActivity).setToolbarTitle(title)
    }

    fun showToolbarUpButton() {
        (activity as MainActivity).setToolbarUpButtonVisible(true)
    }

    fun setOnNavigateUpCallback(callback: () -> Unit) {
        (activity as MainActivity).setOnNavigateUpCallback(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).setToolbarUpButtonVisible(false)
        (activity as MainActivity).setOnNavigateUpCallback(null)
    }
}
