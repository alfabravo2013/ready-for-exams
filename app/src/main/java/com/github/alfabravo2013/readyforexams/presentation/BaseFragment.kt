package com.github.alfabravo2013.readyforexams.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.github.alfabravo2013.readyforexams.MainActivity

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {
    private var _binding: Binding? = null
    val binding: Binding get() = _binding!!

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
    ): Binding

    fun setToolbarTitle(title: String = "") {
        (activity as MainActivity).setToolbarTitle(title)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
