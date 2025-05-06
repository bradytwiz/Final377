package com.app.benhuntoon.final_project

import android.app.Application

class MadLibApplication : Application() {

    companion object {
        lateinit var instance: MadLibApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}