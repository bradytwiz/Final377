package com.app.benhuntoon.final_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.databinding.FragmentCreateMadlibBinding
import com.app.benhuntoon.final_project.viewmodel.MadLibViewModel

class CreateMadLibFragment : Fragment() {
    private var _binding: FragmentCreateMadlibBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MadLibViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMadlibBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MadLibViewModel::class.java)

        setupObservers()
        setupClickListeners()

        return binding.root
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