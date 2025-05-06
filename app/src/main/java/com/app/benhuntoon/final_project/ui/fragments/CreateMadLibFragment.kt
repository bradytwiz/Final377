package com.app.benhuntoon.final_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.data.database.MadLibDatabase
import com.app.benhuntoon.final_project.databinding.FragmentCreateMadlibBinding
import com.app.benhuntoon.final_project.network.RetrofitInstance
import com.app.benhuntoon.final_project.repository.MadLibRepository
import com.app.benhuntoon.final_project.viewmodel.MadLibViewModel
import com.app.benhuntoon.final_project.viewmodel.MadLibViewModelFactory

class CreateMadLibFragment : Fragment() {
    private var _binding: FragmentCreateMadlibBinding? = null
    private val binding get() = _binding!!
    private var currentTemplateId: String? = null

    private val madLibRepository: MadLibRepository by lazy {
        val wordApi = RetrofitInstance.api
        val madLibDao = MadLibDatabase.getDatabase(requireContext()).madLibDao()
        MadLibRepository(wordApi, madLibDao)
    }

    private val viewModel: MadLibViewModel by viewModels {
        MadLibViewModelFactory(madLibRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentTemplateId = it.getString("templateId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMadlibBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentTemplateId?.let { templateId ->
            viewModel.loadTemplate(templateId)
        } ?: run {
            viewModel.loadDefaultTemplate()
        }
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.wordValidationResult.observe(viewLifecycleOwner) { isValid ->
            if (isValid) {
                binding.btnSubmitWord.isEnabled = true
            } else {
                Toast.makeText(context, "Invalid word. Please try another.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.madLibComplete.observe(viewLifecycleOwner) { isComplete ->
            if (isComplete) {
                showCompletedMadLib()
            }
        }

        viewModel.nextPlaceholder.observe(viewLifecycleOwner) { placeholderType ->
            binding.tvWordType.text = placeholderType.capitalize()
        }
    }

    private fun setupClickListeners() {
        binding.btnValidateWord.setOnClickListener {
            val word = binding.etWordInput.text.toString()
            if (word.isNotEmpty()) {
                viewModel.validateWord(word)
            }
        }

        binding.btnSubmitWord.setOnClickListener {
            val word = binding.etWordInput.text.toString()
            viewModel.addWordToMadLib(word)
            binding.etWordInput.text.clear()
            binding.btnSubmitWord.isEnabled = false
        }

        binding.btnSaveMadLib.setOnClickListener {
            viewModel.saveCurrentMadLib()
            Toast.makeText(context, "MadLib saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCompletedMadLib() {
        val completedStory = viewModel.getCompletedStory()
        binding.tvMadlibStory.text = completedStory
        binding.btnSaveMadLib.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}