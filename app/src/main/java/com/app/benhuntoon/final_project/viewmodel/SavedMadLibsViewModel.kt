package com.app.benhuntoon.final_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.benhuntoon.final_project.data.database.MadLibEntity
import com.app.benhuntoon.final_project.repository.MadLibRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedMadLibsViewModel(private val repository: MadLibRepository) : ViewModel() {
    private val _savedMadLibs = MutableStateFlow<List<MadLibEntity>>(emptyList())
    val savedMadLibs: StateFlow<List<MadLibEntity>> = _savedMadLibs.asStateFlow()

    init {
        loadMadLibs()
    }

    private fun loadMadLibs() {
        viewModelScope.launch {
            _savedMadLibs.value = repository.getAllMadLibs()
        }
    }

    fun deleteMadLib(id: Int) {
        viewModelScope.launch {
            repository.deleteMadLib(id)
            loadMadLibs()
        }
    }
}


