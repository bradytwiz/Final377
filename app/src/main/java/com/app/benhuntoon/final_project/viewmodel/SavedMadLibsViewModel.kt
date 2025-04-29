package com.app.benhuntoon.final_project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.app.benhuntoon.final_project.data.database.MadLibEntity
import com.app.benhuntoon.final_project.repository.MadLibRepository
import kotlinx.coroutines.launch

class SavedMadLibsViewModel(private val repository: MadLibRepository) : ViewModel() {

    val savedMadLibs: LiveData<List<MadLibEntity>> = liveData {
        emit(repository.getAllMadLibs())
    }

    fun deleteMadLib(id: Int) {
        viewModelScope.launch {
            repository.deleteMadLib(id)
        }
    }
}