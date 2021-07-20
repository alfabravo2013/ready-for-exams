package com.github.alfabravo2013.readyforexams.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.FragmentHomeBinding
import com.github.alfabravo2013.readyforexams.presentation.BaseFragment
import com.github.alfabravo2013.readyforexams.presentation.home.HomeViewModel.OnEvent
import com.github.alfabravo2013.readyforexams.presentation.models.ChecklistRepresentation
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    private val viewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToCreateScreen() {
        findNavController().navigate(R.id.createFragment)
    }

    private fun showChecklists(checklists: List<ChecklistRepresentation>) {
        binding.itemEmpty.visibility = View.INVISIBLE
        adapter.setItems(checklists)
    }
}
