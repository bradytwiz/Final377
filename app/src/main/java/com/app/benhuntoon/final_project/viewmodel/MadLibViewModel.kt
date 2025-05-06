package com.app.benhuntoon.final_project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.benhuntoon.final_project.repository.MadLibRepository
import com.app.benhuntoon.final_project.data.database.MadLibEntity
import kotlinx.coroutines.launch
import com.google.gson.Gson

class MadLibViewModel(private val repository: MadLibRepository) : ViewModel() {
    private val _wordValidationResult = MutableLiveData<Boolean>()
    val wordValidationResult: LiveData<Boolean> = _wordValidationResult

    private val _madLibComplete = MutableLiveData<Boolean>()
    val madLibComplete: LiveData<Boolean> = _madLibComplete

    private val _nextPlaceholder = MutableLiveData<String>()
    val nextPlaceholder: LiveData<String> = _nextPlaceholder
    data class MadLibData(val template: String, val placeholders: List<String>)

    private val words = mutableListOf<String>()
    private var currentTemplate: String = ""
    private var currentPlaceholderTypes = listOf("adjective", "body part", "object", "smell", "action", "adjective", "body part", "location", "number", "animal", "sound", "number", "appliance", "celebrity")
    private var currentPlaceholderIndex = 0

    private val templates = mapOf(
        "finals_week" to MadLibData(
            "It was the one night everybody dreads, the night before finals week starts at NAU " +
                    "The library was full of [adjective] students all glued to their books and [body part] deep in energy drink cans and empty coffee cups " +
                    "One desperate student even had the guts to sneak in a(n) [object]. " +
                    "As expected I couldn't find a decent place to sit so I had to sit next to the dude who smelled like [smell] " +
                    "I began to [action] [adjective]. " +
                    "Finally, at around 5am, Monday morning I started wandering back to my dorm room, but my [body part] was so exhausted that I decided to crash at [location]. " +
                    "I woke up [number] hours later by a not so friendly [animal] who was eating my notes! " +
                    "[sound] I shouted, I am late for my first final! I ran to class as fast as I could but I saw no one. " +
                    "That's when I realized I had been going to the wrong school for [number] years and was actually a [appliance] " +
                    "'Oh well at least I'm still smarter than [celebrity]' ",
            listOf("adjective", "body part", "object", "smell", "action", "adjective", "body part", "location", "number", "animal", "sound", "number", "appliance", "celebrity")
        ),
        "zoo_trip" to MadLibData(
            "Yesterday, I went to the [adjective] zoo and saw an [animal]",
            listOf("adjective", "animal", /* ... */ )
        ),
        "beach_day" to MadLibData(
            "I love spending my summer days at the [adjective] beach with a [noun] by my side",
            listOf("adjective", "noun", /* ... */ )
        )
        // Add more templates here
    )

    init {
        loadDefaultTemplate()
    }

    fun loadDefaultTemplate() {
        loadTemplate("finals_week")
    }

    fun loadTemplate(templateId: String) {
        templates[templateId]?.let { madLibData ->
            currentTemplate = madLibData.template
            currentPlaceholderTypes = madLibData.placeholders
            words.clear()
            currentPlaceholderIndex = 0
            if (currentPlaceholderTypes.isNotEmpty()) {
                _nextPlaceholder.postValue(currentPlaceholderTypes[currentPlaceholderIndex])
            } else {
                _nextPlaceholder.postValue("")
            }
        } ?: run {
            // Handle the case where the templateId is not found
            _nextPlaceholder.postValue("Error: Template not found")
            currentTemplate = ""
            currentPlaceholderTypes = emptyList()
            words.clear()
            currentPlaceholderIndex = 0
            _madLibComplete.postValue(false)
        }
    }

    fun validateWord(word: String) {
        val currentType = _nextPlaceholder.value
        if (currentType == "adjective" || currentType == "noun" || currentType == "verb") {
            viewModelScope.launch {
                val isValid = repository.validateWord(word)
                _wordValidationResult.postValue(isValid)
            }
        } else {
            _wordValidationResult.postValue(true)
        }
    }

    fun addWordToMadLib(word: String) {
        if (currentPlaceholderIndex < currentPlaceholderTypes.size) {
            words.add(word) // Add to the List
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
        currentPlaceholderTypes.forEachIndexed { index, type ->
            if (index < words.size) {
                story = story.replaceFirst("[$type]", words[index])
            }
        }
        return story
    }

    fun saveCurrentMadLib() {
        val completedStory = getCompletedStory()
        val filledWordsJson = Gson().toJson(words)
        val madLib = MadLibEntity(
            title = "MadLib ${System.currentTimeMillis()}",
            story = completedStory,
            filledWords = filledWordsJson
        )
        viewModelScope.launch {
            repository.saveMadLib(madLib)
            loadTemplate("finals_week")
            _madLibComplete.postValue(false)
        }
    }
}