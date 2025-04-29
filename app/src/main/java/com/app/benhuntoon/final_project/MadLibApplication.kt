package com.app.benhuntoon.final_project

import android.app.Application
import com.app.benhuntoon.final_project.data.database.MadLibDatabase
import com.app.benhuntoon.final_project.repository.MadLibRepository
import com.app.benhuntoon.final_project.network.RetrofitInstance
import com.app.benhuntoon.final_project.network.WordApi
import kotlin.lazy

class MadLibApplication : Application() {
    private val database by lazy { MadLibDatabase.getDatabase(this) }
    private val wordApi by lazy { WordApi(RetrofitInstance.api) }
    val repository by lazy { MadLibRepository(wordApi, database.madLibDao()) }
}