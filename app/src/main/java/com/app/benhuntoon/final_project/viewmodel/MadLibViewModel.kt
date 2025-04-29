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

    private val _nextPlaceholder = MutableLiveData<String>()
    val nextPlaceholder: LiveData<String> = _nextPlaceholder

    private val words = mutableMapOf<String, String>()
    private var currentTemplate: String = ""
    private var currentPlaceholderTypes = listOf("adjective", "noun", "verb", "adjective", "noun", "verb", "adverb")
    private var currentPlaceholderIndex = 0

    init {
        loadTemplate()
    }

    private fun loadTemplate() {
        currentTemplate = "Once upon a time, there was a [adjective] [noun] who loved to [verb]. " +
                "One day, they met a [adjective] [noun] and together they [verb] [adverb]."
        words.clear()
        currentPlaceholderIndex = 0
        if (currentPlaceholderTypes.isNotEmpty()) {
            _nextPlaceholder.postValue(currentPlaceholderTypes[currentPlaceholderIndex])
        } else {
            _nextPlaceholder.postValue("")
        }
    }

        fun validateWord(word: String) {
        viewModelScope.launch {
            val isValid = repository.validateWord(word)
            _wordValidationResult.postValue(isValid)
        }
    }

    fun addWordToMadLib(word: String) {
        if (currentPlaceholderIndex < currentPlaceholderTypes.size) {
            val placeholderType = currentPlaceholderTypes[currentPlaceholderIndex]
            words[placeholderType] = word
            currentPlaceholderIndex++
            if (currentPlaceholderIndex < currentPlaceholderTypes.size) {
                _nextPlaceholder.postValue(currentPlaceholderTypes[currentPlaceholderIndex])
            } else {
                _madLibComplete.postValue(true)
                _nextPlaceholder.postValue("")
            }
        }
    }

    fun getCompletedStory(): String {
        var story = currentTemplate
        words.forEach { (type, word) ->
            story = story.replaceFirst("[$type]", word)
        }
        return story
    }

    fun saveCurrentMadLib() {
        val completedStory = getCompletedStory()
        val filledWordsJson = com.google.gson.Gson().toJson(words)
        val madLib = MadLibEntity(
            title = "MadLib ${System.currentTimeMillis()}",
            story = completedStory,
            filledWords = filledWordsJson
        )
        viewModelScope.launch {
            repository.saveMadLib(madLib)
            loadTemplate()
            _madLibComplete.postValue(false)
        }
    }
}
