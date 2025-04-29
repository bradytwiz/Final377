package com.app.benhuntoon.final_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.databinding.FragmentSavedMadlibsBinding
import com.app.benhuntoon.final_project.viewmodel.SavedMadLibsViewModel

class SavedMadLibsFragment : Fragment() {
    private var _binding: FragmentSavedMadlibsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SavedMadLibsViewModel
    private lateinit var madLibsAdapter: MadLibsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedMadlibsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SavedMadLibsViewModel::class.java)

        setupRecyclerView()
        observeMadLibs()

        return binding.root
    }

    private fun setupRecyclerView() {
        madLibsAdapter = MadLibsAdapter { madLib ->
            viewModel.deleteMadLib(madLib.id)
        }

        binding.rvSavedMadlibs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = madLibsAdapter
        }
    }

    private fun observeMadLibs() {
        viewModel.savedMadLibs.observe(viewLifecycleOwner) { madLibs ->
            madLibsAdapter.submitList(madLibs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}