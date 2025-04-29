package com.app.benhuntoon.final_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.benhuntoon.final_project.repository.MadLibRepository

class MadLibViewModelFactory(private val repository: MadLibRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MadLibViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MadLibViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}