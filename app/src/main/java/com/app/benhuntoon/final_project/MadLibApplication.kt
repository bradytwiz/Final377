package com.app.benhuntoon.final_project

import android.app.Application
import com.app.benhuntoon.final_project.data.database.MadLibDatabase
import com.app.benhuntoon.final_project.repository.MadLibRepository
import com.app.benhuntoon.final_project.network.RetrofitInstance

class MadLibApplication : Application() {
    private val database by lazy { MadLibDatabase.getDatabase(this) }
    val repository by lazy { MadLibRepository(RetrofitInstance.api, database.madLibDao()) }

    companion object {
        lateinit var instance: MadLibApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}