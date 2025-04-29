package com.app.benhuntoon.final_project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.benhuntoon.final_project.repository.MadLibRepository
import com.app.benhuntoon.final_project.data.database.MadLibEntity
import kotlinx.coroutines.launch

class MadLibViewModel(private val repository: MadLibRepository) : ViewModel() {
    private val _wordValidationResult = MutableLiveData<Boolean>()
    val wordValidationResult: LiveData<Boolean> = _wordValidationResult

    private val _madLibComplete = MutableLiveData<Boolean>()
    val madLibComplete: LiveData<Boolean> = _madLibComplete

    private val words = mutableListOf<String>()
    private var currentTemplate: String = ""

    init {
        loadTemplate()
    }

    private fun loadTemplate() {
        currentTemplate = "Once upon a time, there was a [adjective] [noun] who loved to [verb]. " +
                "One day, they met a [adjective] [noun] and together they [verb] [adverb]."
        words.clear()
    }

    fun validateWord(word: String) {
        viewModelScope.launch {
            try {
                val response = repository.validateWord(word)
                _wordValidationResult.postValue(response.isSuccessful)
            } catch (e: Exception) {
                _wordValidationResult.postValue(false)
            }
        }
    }

    fun addWordToMadLib(word: String) {
        words.add(word)
        if (words.size == 6) { // Number of placeholders in our template
            _madLibComplete.postValue(true)
        }
    }

    fun getCompletedStory(): String {
        var story = currentTemplate
        words.forEachIndexed { index, word ->
            story = story.replaceFirst("[...]", word)
        }
        return story
    }

    fun saveCurrentMadLib() {
        val completedStory = getCompletedStory()
        val madLib = MadLibEntity(
            title = "MadLib ${System.currentTimeMillis()}",
            story = completedStory,
            filledWords = words
        )
        viewModelScope.launch {
            repository.saveMadLib(madLib)
            loadTemplate() // Reset for new MadLib
            _madLibComplete.postValue(false)
        }
    }
}