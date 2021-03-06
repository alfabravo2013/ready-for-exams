package com.github.alfabravo2013.readyforexams.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentHomeBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.github.alfabravo2013.readyforexams.presentation.home.HomeViewModel.OnEvent
import com.github.alfabravo2013.readyforexams.presentation.models.ChecklistRepresentation
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModel()

    private val adapter: ChecklistAdapter by lazy { ChecklistAdapter() }

    private val onEventObserver = Observer<OnEvent> { event ->
        when (event) {
            is OnEvent.NavigateToCreateScreen -> navigateToCreateScreen()
            is OnEvent.EmptyList -> binding.itemEmpty.visibility = View.VISIBLE
            is OnEvent.LoadChecklists -> showChecklists(event.checklists)
            is OnEvent.ShowProgress -> binding.homeProgressBar.visibility = View.VISIBLE
            is OnEvent.HideProgress -> binding.homeProgressBar.visibility = View.GONE
        }
    }

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle(requireContext().getString(R.string.home_title_text))

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        with(binding) {
            homeChecklistRecyclerView.adapter = adapter
            homeAddChecklistButton.setOnClickListener {
                viewModel.onAddChecklistButtonClick()
            }
        }

        viewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchChecklists()
    }

    private fun navigateToCreateScreen() {
        findNavController().navigate(R.id.createFragment)
    }

    private fun showChecklists(checklists: List<ChecklistRepresentation>) {
        binding.itemEmpty.visibility = View.INVISIBLE
        adapter.setItems(checklists)
    }
}
