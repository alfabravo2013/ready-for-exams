package com.github.alfabravo2013.readyforexams.presentation.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentHomeBinding
import com.github.alfabravo2013.readyforexams.domain.models.Checklist
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.github.alfabravo2013.readyforexams.presentation.home.HomeViewModel.OnEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        setToolbarTitle(requireContext().getString(R.string.home_title_text))

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

    private fun showChecklists(checklists: List<Checklist>) {
        binding.itemEmpty.visibility = View.INVISIBLE
        adapter.setItems(checklists)
    }
}
