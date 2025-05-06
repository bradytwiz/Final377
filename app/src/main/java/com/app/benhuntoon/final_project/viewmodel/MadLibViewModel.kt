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
            "It was the one night everybody dreads, the night before finals week starts at NAU The library was full of [adjective] students all glued to their books and [body part] deep in energy drink cans and empty coffee cups One desperate student even had the guts to sneak in a(n) [object]. As expected I couldn't find a decent place to sit so I had to sit next to the dude who smelled like [smell] I began to [action] [adjective]. Finally, at around 5am, Monday morning I started wandering back to my dorm room, but my [body part] was so exhausted that I decided to crash at [location]. I woke up [number] hours later by a not so friendly [animal] who was eating my notes! [sound] I shouted, I am late for my first final! I ran to class as fast as I could but I saw no one. That's when I realized I had been going to the wrong school for [number] years and was actually a [appliance] 'Oh well at least I'm still smarter than [celebrity]' ",
            listOf("adjective", "body part", "object", "smell", "action", "adjective", "body part", "location", "number", "animal", "sound", "number", "appliance", "celebrity")
        ),
        "zoo_trip" to MadLibData(
            "Yesterday, our class took a trip to the city's biggest [adjective] zoo! First, we saw a [noun] munching on some leaves. It was so [adverb]! Then, we walked over to the reptile house and saw a huge [color] snake. Suddenly, it started to [verb]! Everyone let out a little [exclamation]. My favorite part was watching the [plural noun] play in the [adjective] water.",
            listOf("adjective", "noun", "adverb", "color", "verb", "exclamation", "plural noun", "adjective")
        ),
        "beach_day" to MadLibData(
            "Yesterday was such a [adjective] beach day! The sun was shining [adverb] and the sand felt like [noun] between my toes. I decided to [verb] in the ocean, which was surprisingly [adjective]. Suddenly, a [color] crab scuttled by and tried to [verb] my [body part]! I let out a little [exclamation]. Later, I built a magnificent [adjective] sandcastle and then ate a [food]. What a [adjective] day!",
            listOf("adjective", "adverb", "noun", "verb", "adjective", "color", "verb", "body part", "exclamation", "adjective", "food", "adjective")
        ),
        "weirdest_day" to MadLibData(
            "Today was the [adjective] day ever. First, I saw a [noun] [verb] down the street. Then, my [body part] started to [verb] uncontrollably. Out of nowhere, a [color] [animal] offered me a [food]. I couldn't help but shout [exclamation]! What a truly [adjective] day!",
            listOf("adjective", "noun", "verb", "body part", "verb", "color", "animal", "food", "exclamation", "adjective")
        ),
        "math_class" to MadLibData(
            // math jack story time!
            "",
            listOf()
        ),
        "setup_chairs" to MadLibData(
            "The task of setting up chairs for the [adjective] event began. First, we had to move all the [plural noun] from the [room] to the [outdoor place]. It felt like we had to carry number] chairs! One person even tried to [verb] a stack of chairs and ended up [adverb] falling. We had to arrange them in [shape] rows. Then, we argued about whether the chairs should face the [direction] or the [direction]. Finally, after much frustration, the last [color] chair was in place. What a [adjective] job!",
            listOf("adjective", "plural noun", "room", "outdoor place", "number", "verb", "adverb", "shape", "direction", "direction",  "color", "tiring adjective")
        ),
        "free_cake" to MadLibData(
            "Walking down the street, I suddenly saw a sign that said 'Free [adjective] Cake!' I couldn't believe it. I quickly [verb] inside and saw a [noun] holding a giant [color] cake. They offered me a [adjective] slice. Must have been my lucky day!",
            listOf("adjective", "verb", "noun", "color", "adjective")
        ),
        "best_burger" to MadLibData(
            "Yesterday, I ate the [adjective] burger ever! The patty was so [adjective] and juicy. It was topped with [food], [food], and a [color] sauce. The [adjective] bun was toasted to perfection. I took one [adjective] bite and immediately wanted [number] more. The chef, who was a [noun], told me their secret ingredient was [liquid]. I will definitely [verb] this burger again soon.",
            listOf("adjective", "adjective", "food", "food", "color", "adjective", "adjective", "number", "noun", "liquid", "verb")
        ),
        "fancy_restaurant" to MadLibData(
            "Last night, I went to a very [adjective] restaurant. The waiter, who was wearing a [item], seated us at a table with a [metal] tablecloth. For an appetizer, I ordered [food]. Then, for my main course, I had [food] with a side of [vegetable]. The whole experience felt very [adjective], but the prices were [adjective]!, [number]/10",
            listOf("adjective", "item", "metal", "food", "food", "vegetable", "adjective", "adjective", "number")
        ),
        "best_movie" to MadLibData(
            "The best movie in the world starts with a [adjective] opening scene where a [noun] is [verb]ing across a [adjective] landscape in [country]. The main character, a [adjective] [occupation], discovers a [object] that holds the secret to [noun]. Suddenly, a [adjective] [animal] appears and tries to [verb] the [object]. There's a thrilling chase scene involving a [vehicle] and a lot of [sound effect]. The [adjective] soundtrack makes you want to [verb]. In the end, the [noun] and the [adjective] [occupation] team up to [verb] the [noun] and everyone celebrates with a giant [food]. The final shot is of a [color] [noun] [verb]ing into the sunset. Absolute Cinema!",
            listOf("adjective", "noun", "verb", "adjective", "country", "adjective", "occupation", "object", "noun", "adjective", "animal", "verb", "object", "vehicle", "sound effect", "adjective", "verb", "noun", "adjective", "occupation", "verb", "noun", "food", "color", "noun", "verb")
        ),
        "pie_contest" to MadLibData(
            "The annual pie contest was a [adjective] event! Bakers brought their most [adjective] creations. The judges had to [verb] each [flavor] pie very carefully. In the end, the winner received a [item]!",
            listOf("adjective", "adjective", "verb", "flavor", "item")
        ),
        "guys_night" to MadLibData(
            "Last Friday was the ultimate guys' night! We decided to start by going to a [adjective] place to eat some [food]. After that, we went to play [game] at a really [adjective] arcade. My buddy [nickname] was surprisingly good at [game]. Later, we went back to [name]'s place and watched a [adjective] [movie genre]. We all ended up [verb]ing really [adverb] and telling [adjective] stories until the [time of day]. It was a truly [adjective] night!",
            listOf("adjective", "food", "game", "adjective", "nickname", "game", "name", "adjective", "movie genre", "verb", "adverb", "adjective", "time of day", " adjective")
        ),
        "longest_weekend" to MadLibData(
            "This past weekend felt like the [adjective] longest weekend ever. It started on a [weekday] that felt like a [weekday]. I had planned to [exciting verb], but instead I mostly just [lazy verb] around my [room in a house]. The weather was incredibly [unpleasant adjective], so going [fun outdoor activity] was out of the question. Even the food I ate tasted [adjective]. My [relative] kept calling and talking for [number] hours. The most exciting thing that happened was watching a [color] [animal] [verb] across the [furniture]. Finally, [weekday] arrived, and it felt like a [number] [unit of time](s) had passed!",
            listOf("adjective", "weekday", "weekday", "verb", "verb", "room", "adjective", "activity", "adjective", "relative", "number", "color", "animal", "verb", "furniture", "weekday", "number", "unit of time")
        )
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