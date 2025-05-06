package com.app.benhuntoon.final_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.benhuntoon.final_project.data.database.MadLibDatabase
import com.app.benhuntoon.final_project.databinding.FragmentCreateMadlibBinding
import com.app.benhuntoon.final_project.network.RetrofitInstance
import com.app.benhuntoon.final_project.repository.MadLibRepository
import com.app.benhuntoon.final_project.viewmodel.MadLibViewModel
import com.app.benhuntoon.final_project.viewmodel.MadLibViewModelFactory

//fragment to hold story/word entry window
class CreateMadLibFragment : Fragment() {
    //initialize binding data
    private var _binding: FragmentCreateMadlibBinding? = null
    private val binding get() = _binding!!
    private var currentTemplateId: String? = null

    //set up the repository for the fragment
    private val madLibRepository: MadLibRepository by lazy {
        val wordApi = RetrofitInstance.api
        val madLibDao = MadLibDatabase.getDatabase(requireContext()).madLibDao()
        MadLibRepository(wordApi, madLibDao)
    }

    //initialize the view model
    private val viewModel: MadLibViewModel by viewModels {
        MadLibViewModelFactory(madLibRepository)
    }

    //create an id for the current story template
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentTemplateId = it.getString("templateId")
        }
    }

    //set up word entry view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMadlibBinding.inflate(inflater, container, false)
        return binding.root
    }

    // initialize the view for user input
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

    // set up observers
    private fun setupObservers() {
        viewModel.wordValidationResult.observe(viewLifecycleOwner) { isValid ->
            //if word is valid enable the button to submit
            if (isValid) {
                binding.btnSubmitWord.isEnabled = true
                // otherwise display error
            } else {
                Toast.makeText(context, "Invalid word. Please try another.", Toast.LENGTH_SHORT).show()
            }
        }

        // show the full story when complete
        viewModel.madLibComplete.observe(viewLifecycleOwner) { isComplete ->
            if (isComplete) {
                showCompletedMadLib()
            }
        }

        // display what kind of word is needed for the entry
        viewModel.nextPlaceholder.observe(viewLifecycleOwner) { placeholderType ->
            binding.tvWordType.text = placeholderType.capitalize()
        }
    }

    //listen for input
    private fun setupClickListeners() {
        //handle validate button
        binding.btnValidateWord.setOnClickListener {
            val word = binding.etWordInput.text.toString()
            if (word.isNotEmpty()) {
                viewModel.validateWord(word)
            }
        }

        // handle submit button
        binding.btnSubmitWord.setOnClickListener {
            val word = binding.etWordInput.text.toString()
            viewModel.addWordToMadLib(word)
            binding.etWordInput.text.clear()
            binding.btnSubmitWord.isEnabled = false
        }

        //handle save button
        binding.btnSaveMadLib.setOnClickListener {
            viewModel.saveCurrentMadLib()
            Toast.makeText(context, "MadLib saved!", Toast.LENGTH_SHORT).show()
        }
    }

    //display the full finished story when complete
    private fun showCompletedMadLib() {
        val completedStory = viewModel.getCompletedStory()
        binding.tvMadlibStory.text = completedStory
        binding.btnSaveMadLib.visibility = View.VISIBLE
    }

    //handle fragment destruction
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}