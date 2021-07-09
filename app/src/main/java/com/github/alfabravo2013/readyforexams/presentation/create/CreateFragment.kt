package com.github.alfabravo2013.readyforexams.presentation.create

import android.os.Bundle
import android.view.View
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentCreateBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateFragment : BaseFragment(R.layout.fragment_create) {
    private val viewModel: CreateViewModel by viewModel()
    private lateinit var binding: FragmentCreateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreateBinding.bind(view)
        setToolbarTitle(requireContext().getString(R.string.create_title_text))
    }
}
