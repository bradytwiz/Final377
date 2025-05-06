package com.app.benhuntoon.final_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.benhuntoon.final_project.data.database.MadLibDatabase
import com.app.benhuntoon.final_project.databinding.FragmentSavedMadlibsBinding
import com.app.benhuntoon.final_project.network.RetrofitInstance
import com.app.benhuntoon.final_project.repository.MadLibRepository
import com.app.benhuntoon.final_project.viewmodel.SavedMadLibsViewModel
import com.app.benhuntoon.final_project.viewmodel.SavedMadLibsViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//class fragment to hold the saved list of madlibs
class SavedMadLibsFragment : Fragment() {
    private var _binding: FragmentSavedMadlibsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SavedMadLibsViewModel
    private lateinit var madLibsAdapter: MadLibsAdapter

    //inflates the layout for view binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedMadlibsBinding.inflate(inflater, container, false)

        val dao = MadLibDatabase.getDatabase(requireContext()).madLibDao()
        val api = RetrofitInstance.api
        val repository = MadLibRepository(api, dao)

        //initialize the view model
        val factory = SavedMadLibsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SavedMadLibsViewModel::class.java)

        //set up display and collect saved madlibs
        setupRecyclerView()
        observeMadLibs()
        //return the binding
        return binding.root
    }

    //initialize display for each saved madlib
    private fun setupRecyclerView() {
        madLibsAdapter = MadLibsAdapter { madLib ->
            viewModel.deleteMadLib(madLib.id)
        }
        binding.rvSavedMadlibs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = madLibsAdapter
        }
    }

    //story collection function
    private fun observeMadLibs() {
        //collect the list of saved stories
        viewModel.savedMadLibs.onEach { madLibs ->
            madLibsAdapter.submitList(madLibs)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    //handle fragment destruction
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}